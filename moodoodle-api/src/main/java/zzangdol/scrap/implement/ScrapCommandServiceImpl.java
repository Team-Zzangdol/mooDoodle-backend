package zzangdol.scrap.implement;

import groovy.util.logging.Slf4j;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.constant.Constants;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.CategoryNotFoundException;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.exception.custom.ScrapNotFoundException;
import zzangdol.scrap.dao.CategoryRepository;
import zzangdol.scrap.dao.ScrapRepository;
import zzangdol.scrap.domain.Category;
import zzangdol.scrap.domain.Scrap;
import zzangdol.scrap.domain.ScrapCategory;
import zzangdol.user.domain.User;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ScrapCommandServiceImpl implements ScrapCommandService {

    private final ScrapRepository scrapRepository;
    private final CategoryRepository categoryRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public void handleScrap(User user, Long diaryId) {
        Optional<Scrap> optionalScrap = scrapRepository.findScrapByUserAndDiaryId(user, diaryId);
        if (optionalScrap.isPresent()) {
            scrapRepository.delete(optionalScrap.get());
        } else {
            Diary diary = diaryRepository.findById(diaryId)
                    .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
            Scrap scrap = buildScrap(user, diary);

            Category defaultCategory = categoryRepository.findCategoryByUserAndName(user, Constants.DEFAULT_CATEGORY_NAME)
                    .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
            scrap = scrapRepository.save(scrap);
            addCategoryToScrap(user, scrap.getId(), defaultCategory.getId());
        }
    }

    private Scrap buildScrap(User user, Diary diary) {
        return Scrap.builder()
                .user(user)
                .diary(diary)
                .build();
    }

    @Override
    public void addCategoryToScrap(User user, Long scrapId, Long categoryId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> ScrapNotFoundException.EXCEPTION);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
        ScrapCategory scrapCategory = buildScrapCategory(scrap, category);
        scrap.addCategory(scrapCategory);
        category.addScrapCategory(scrapCategory);
    }

    @Override
    public void deleteScrap(User user, Long scrapId) {
        Scrap scrap = scrapRepository.findById(scrapId)
                .orElseThrow(() -> ScrapNotFoundException.EXCEPTION);

    }

    private ScrapCategory buildScrapCategory(Scrap scrap, Category category) {
        return ScrapCategory.builder()
                .scrap(scrap)
                .category(category)
                .build();
    }

}
