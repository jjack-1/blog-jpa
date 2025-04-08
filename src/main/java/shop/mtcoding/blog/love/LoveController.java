package shop.mtcoding.blog.love;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.mtcoding.blog.user.User;

@Controller
@RequiredArgsConstructor
public class LoveController {
    private final LoveService loveService;
    private final HttpSession session;

    @PostMapping("/love")
    public void save(@RequestBody LoveRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        loveService.좋아요(saveDTO, sessionUser);
    }
}
