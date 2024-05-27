package zzangdol.scrap.implement;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.diary.dao.DiaryRepository;
import zzangdol.diary.domain.Diary;
import zzangdol.exception.custom.CategoryNotFoundException;
import zzangdol.exception.custom.DiaryNotFoundException;
import zzangdol.exception.custom.ScrapDuplicateException;
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
    public Scrap createScrap(User user, Long diaryId) {
        checkScrapDuplication(user, diaryId);
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> DiaryNotFoundException.EXCEPTION);
        Scrap scrap = buildScrap(user, diary);
        return scrapRepository.save(scrap);
    }

    private void checkScrapDuplication(User user, Long diaryId) {
        if (scrapRepository.findScrapByUserAndDiaryId(user, diaryId).isPresent()) {
            throw ScrapDuplicateException.EXCEPTION;
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
        scrapRepository.delete(scrap);
    }

    private ScrapCategory buildScrapCategory(Scrap scrap, Category category) {
        return ScrapCategory.builder()
                .scrap(scrap)
                .category(category)
                .build();
    }

}
