package shop.mtcoding.blog._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog._core.error.ex.*;
import shop.mtcoding.blog._core.util.Resp;

@RestControllerAdvice
// 모든 exception을 다 받는다. dispatcher servlet이 모든 ex // @RestControllerAdvice -> 데이터 리턴, @ControllerAdvice -> 파일 리턴
public class GlobalExceptionHandler {

    // 400 -> 잘못된 요청
    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e) {
        String html = """
                <script>
                    alert('${msg}');
                </script>
                """.replace("${msg}", e.getMessage());
        return html; // 브라우저는 html text를 받으면 해석한다
    }

    // 400 -> 잘못된 요청
    @ExceptionHandler(ExceptionApi400.class)
    public Resp<?> exApi400(ExceptionApi400 e) {
        return Resp.fail(400, e.getMessage());
    }

    // 401 -> 인증 안됐을때
    @ExceptionHandler(Exception401.class) // catch 부분에서 실행되는 메서드
    public String ex401(Exception401 e) {
        String html = """
                <script>
                    alert('${msg}');
                    location.href = '/login-form';
                </script>
                """.replace("${msg}", e.getMessage());
        return html;
    }

    // 401 -> 인증 안됐을때
    @ExceptionHandler(ExceptionApi401.class)
    public Resp<?> exApi401(ExceptionApi401 e) {
        return Resp.fail(401, e.getMessage());
    }

    // 403 -> 권한 없음
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e) {
        String html = """
                <script>
                    alert('${msg}');
                </script>
                """.replace("${msg}", e.getMessage());
        return html;
    }

    // 403 -> 권한 없음
    @ExceptionHandler(ExceptionApi403.class)
    public Resp<?> exApi403(ExceptionApi403 e) {
        return Resp.fail(403, e.getMessage());
    }

    // 404 -> 자원 없음
    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e) {
        String html = """
                <script>
                    alert('${msg}');
                </script>
                """.replace("${msg}", e.getMessage());
        return html;
    }

    // 404 -> 자원 없음
    @ExceptionHandler(ExceptionApi404.class)
    public Resp<?> exApi404(ExceptionApi404 e) {
        return Resp.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class) // 알지 못하는 모든 에러를 처리하는 방법
    public String exUnknown(Exception e) {
        String html = """
                <script>
                    alert('${msg}');
                    history.back();
                </script>
                """.replace("${msg}", "관리자에게 문의해주세요");
        System.out.println("관리자님 보세요 : " + e.getMessage());
        return html;
    }
}
