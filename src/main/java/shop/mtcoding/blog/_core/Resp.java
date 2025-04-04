package shop.mtcoding.blog._core;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data // getter가 있어야 @ResponseBody 이 어노테이션으로 json으로 변환해 준다
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    // 앞부분의 <B> 는 받는 타입을 임시로 Object로 받아라고 하는 문법
    public static <B> Resp<?> ok(B body) {
        return new Resp<>(200, "성공", body);
    }

    public static Resp<?> fail(Integer status, String msg) {
        return new Resp<>(status, msg, null);
    }
}