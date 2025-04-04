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
