package shop.mtcoding.blog.user;

import lombok.Data;

public class UserRequest {

    // insert 용도의 dto에는 toEntity 메서드를 만든다
    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        // dto에 있는 데이터를 바로 Entity 객체로 변환 하는 메서드
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
        private String username;
        private String password;
        private String rememberMe;
    }
}
