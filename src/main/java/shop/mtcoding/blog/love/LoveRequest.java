package shop.mtcoding.blog.love;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

public class LoveRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "정상적인 접근이 아닙니다")
        private Integer boardId;

        public Love toEntity(Integer sessionUserId) {
            return Love.builder()
                    .user(User.builder().id(sessionUserId).build())
                    .board(Board.builder().id(boardId).build()) // board 객체에 id만 넣어서 insert를 해도 자동으로 이 객체의 키값을 외래키로 적용함
                    .build();
        }
    }
}