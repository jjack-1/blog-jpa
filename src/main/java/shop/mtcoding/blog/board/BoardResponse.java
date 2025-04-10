package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.reply.Reply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    // 상세보기 화면에 필요한 데이터
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Boolean isPublic;
        private Boolean isOwner;
        private String username;
        private Timestamp createdAt;
        private Boolean isLove;
        private Integer loveCount;
        private Integer loveId;
        private List<ReplyDTO> replies;

        @Data
        public class ReplyDTO {
            private Integer id;
            private String username;
            private Boolean isOwner;
            private String content;

            public ReplyDTO(Reply reply, Integer sessionUserId) {
                this.id = reply.getId();
                this.username = reply.getUser().getUsername();
                this.isOwner = reply.getUser().getId().equals(sessionUserId); // 이 변수가 필요해서 DTO를 만듬
                this.content = reply.getContent();
            }
        }

        public DetailDTO(Board board, Integer sessionUserId, Boolean isLove, Integer loveCount, Integer loveId) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isPublic = board.getIsPublic();
            this.isOwner = board.getUser().getId() == sessionUserId;
            this.username = board.getUser().getUsername();
            this.createdAt = board.getCreatedAt();
            this.isLove = isLove;
            this.loveCount = loveCount;
            this.loveId = loveId;

            List<ReplyDTO> replyDTOS = new ArrayList<>();

            for (Reply reply : board.getReplies()) {
                replyDTOS.add(new ReplyDTO(reply, sessionUserId));
            }

            this.replies = replyDTOS;
        }
    }
}
