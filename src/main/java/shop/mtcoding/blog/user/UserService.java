package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/*
 * 비지니스 로직
 * 트랜잭션 처리
 * DTO 완료
 * */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 해당 username이 사용 중인지 확인
        User alreadyUser = userRepository.findByUsername(joinDTO.getUsername());

        // 2. 사용 중이면 예외!
        if (alreadyUser != null) {
            throw new RuntimeException("해당 username은 이미 사용중 입니다");
        }

        // 3. 아니면 회원가입 성공
        userRepository.save(joinDTO.toEntity());
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

    public Map<String, Object> 유저네임중복체크(String username) {
        User user = userRepository.findByUsername(username);
        Map<String, Object> dto = new HashMap<>();

        if (user == null) {
            dto.put("available", true);
        } else {
            dto.put("available", false);
        }

        return dto;
    }
}
