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
        User user = joinDTO.toEntity(); // 1. 비영속 객체
        System.out.println(user);
        userRepository.save(user);
        System.out.println(user); // 3. user -> db와 동기화

        /* 나중에 정리하기
        System.out.println("------------------------------");
        userRepository.findById(4);
        System.out.println("------------------------------");
        */
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        // 1. username에 대한 데이터가 있는지 확인
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 2. 없으면 예외!
        if (user == null) {
            throw new RuntimeException("해당 아이디가 없습니다");
        }

        // 3. 있으면 password 비교
        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
            throw new RuntimeException("password가 맞지 않습니다");
        }

        return user;
    }
}
