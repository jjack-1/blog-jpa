package shop.mtcoding.blog.love;

import lombok.Data;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

public class LoveRequest {

    @Data
    public static class SaveDTO {
        private Integer boardId;

        public Love toEntity(User sessionUser) {
            return Love.builder()
                    .user(sessionUser)
                    .board(Board.builder().id(boardId).build()) // board 객체에 id만 넣어서 insert를 해도 자동으로 이 객체의 키값을 외래키로 적용함
                    .build();
        }
    }
}