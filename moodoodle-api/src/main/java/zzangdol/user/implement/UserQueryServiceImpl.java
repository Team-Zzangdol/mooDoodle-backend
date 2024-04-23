package zzangdol.user.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzangdol.moodoodlecommon.exception.custom.UserNotFoundException;
import zzangdol.user.dao.UserRepository;
import zzangdol.user.domain.User;

@RequiredArgsConstructor
@Transactional
@Service
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

}
