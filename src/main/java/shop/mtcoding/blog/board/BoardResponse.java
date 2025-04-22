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

    @Data
    public static class DTO {
        private List<Board> boards;
        private Integer current;
        private Integer next;
        private Integer prev;
        private Integer totalCount;
        private Integer size;
        private Integer totalPages;
        private Boolean isFirst; // currentPage를 알아야 한다
        private Boolean isLast; // totalCount, size=3, totalPage를 알아야 한다
        private List<Integer> numbers;
        private Integer pageSize;

        public DTO(List<Board> boards, Integer current, Integer totalCount) {
            this.boards = boards;
            this.next = current + 1;
            this.prev = current - 1;
            this.current = current;
            this.totalCount = totalCount;
            this.size = 3;
            this.totalPages = makeTotalPages(totalCount, this.size);
            this.isFirst = current == 0;
            this.isLast = current == this.totalPages - 1;
            this.numbers = makeNumbers(current, this.totalPages);
        }

        private Integer makeTotalPages(int totalCount, int size) {
            int rest = totalCount % size > 0 ? 1 : 0;
            return totalCount / size + rest;
        }

        private List<Integer> makeNumbers(int current, int totalPage) {
            List<Integer> numbers = new ArrayList<>();

            int start = (current / 5) * 5;
            int end = Math.min(start + 5, totalPage);

            for (int i = start; i < end; i++) {
                numbers.add(i);
            }

            return numbers;
        }
    }
}
