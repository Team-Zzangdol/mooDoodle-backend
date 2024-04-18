package zzangdol.moodoodleapi.user.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;
import zzangdol.moodoodlecommon.exception.custom.MemberNotFoundException;

@Transactional
@RequiredArgsConstructor
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new MemberNotFoundException());
    }

}
