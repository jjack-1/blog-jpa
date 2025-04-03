package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
