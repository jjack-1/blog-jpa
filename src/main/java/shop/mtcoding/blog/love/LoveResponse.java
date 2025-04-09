package shop.mtcoding.blog.love;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LoveResponse {

    @Data
    @AllArgsConstructor
    public static class SaveDTO {
        private Integer loveId;
        private Long loveCount;
    }
}
