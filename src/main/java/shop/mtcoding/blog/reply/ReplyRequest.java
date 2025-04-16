package shop.mtcoding.blog.reply;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "정상적인 접근이 아닙니다")
        private Integer boardId;
        @NotEmpty(message = "댓글 내용을 작성해 주세요")
        private String content;

        public Reply toEntity(Integer sessionUserId) {
            return Reply.builder()
                    .content(content)
                    .board(Board.builder().id(boardId).build())
                    .user(User.builder().id(sessionUserId).build())
                    .build();
        }
    }
}
