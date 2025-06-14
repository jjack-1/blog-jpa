package shop.mtcoding.blog.love;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.Resp;
import shop.mtcoding.blog.user.User;

@RestController
@RequiredArgsConstructor
public class LoveController {
    private final LoveService loveService;
    private final HttpSession session;

    @PostMapping("/api/love")
    public Resp<?> saveLove(@Valid @RequestBody LoveRequest.SaveDTO reqDTO, Errors errors) { // reqDTO -> 컨벤션 약속
        User sessionUser = (User) session.getAttribute("sessionUser");

        LoveResponse.SaveDTO respDTO = loveService.좋아요(reqDTO, sessionUser.getId());

        return Resp.ok(respDTO);
    }

    @DeleteMapping("/api/love/{id}")
    public Resp<?> deleteLove(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        LoveResponse.DeleteDTO respDTO = loveService.좋아요취소(id, sessionUser.getId());

        return Resp.ok(respDTO);
    }
}
