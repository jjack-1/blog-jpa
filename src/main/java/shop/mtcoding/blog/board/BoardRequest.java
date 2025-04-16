package shop.mtcoding.blog.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        // title=제목1&content=내용1 -> isPublic은 null
        // title=제목1&content=내용1&isPublic -> isPublic은 ""
        // title=제목1&content=내용1&isPublic=  -> isPublic은 스페이스
        @NotEmpty(message = "제목을 입력하세요") // null, 스페이스, "" -> 안됨
        private String title;
        @NotEmpty(message = "내용을 입력하세요")
        private String content;
        private String isPublic;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .isPublic(isPublic == null ? false : true)
                    .user(user) // user 객체 필요
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "제목을 입력하세요")
        private String title;
        @NotEmpty(message = "내용을 입력하세요")
        private String content;
        private String isPublic;
    }
}
