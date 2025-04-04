package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 해당 username이 사용 중인지 확인
        User user = userRepository.findByUsername(joinDTO.getUsername());
        // 2. 사용 중이면 예외!
        if (user != null) {
            throw new RuntimeException("해당 username은 이미 사용중 입니다");
        }
        // 3. 아니면 회원가입 성공
        userRepository.save(joinDTO.getUsername(), joinDTO.getPassword(), joinDTO.getEmail());
    }

    @Transactional
    public void 회원가입2(UserRequest.JoinDTO joinDTO) {
        // 1. 해당 username이 사용 중인지 확인
        User alreadyUser = userRepository.findByUsername(joinDTO.getUsername());
        // 2. 사용 중이면 예외!
        if (alreadyUser != null) {
            throw new RuntimeException("해당 username은 이미 사용중 입니다");
        }
        // 3. 아니면 회원가입 성공
        User user = User.builder().username(joinDTO.getUsername()).password(joinDTO.getPassword()).email(joinDTO.getEmail()).build();
        userRepository.save2(user);
    }
}
