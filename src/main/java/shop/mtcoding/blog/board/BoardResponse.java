package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class BoardResponse {

    // 상세보기 화면에 필요한 데이터
    @Data
    @AllArgsConstructor
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
    }
}
