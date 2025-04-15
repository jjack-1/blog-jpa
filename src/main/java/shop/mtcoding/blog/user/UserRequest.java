package shop.mtcoding.blog.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    // insert 용도의 dto에는 toEntity 메서드를 만든다
    @Data
    public static class JoinDTO {
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "유저네임은 2-20자이며, 영어와 숫자만 가능합니다")
        private String username;
        @Size(min = 4, max = 20)
        private String password;
        @Pattern(regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 적어주세요")
        private String email;

        // dto에 있는 데이터를 바로 Entity 객체로 변환 하는 메서드 insert에 필요함
        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "유저네임은 2-20자이며, 영어와 숫자만 가능합니다")
        private String username;
        @Size(min = 4, max = 20)
        private String password;
        private String rememberMe;
    }

    @Data
    public static class UpdateDTO {
        @Size(min = 4, max = 20)
        private String password;
        @Pattern(regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 적어주세요")
        private String email;
    }
}
