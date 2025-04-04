package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.Resp;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        System.out.println(joinDTO);
        userService.회원가입(joinDTO);
        return "redirect:/join-form";
    }


    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        System.out.println(loginDTO);
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/login-form";
    }

    @GetMapping("/check-username-available/{username}")
    public @ResponseBody Resp<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.유저네임중복체크(username);

        return Resp.ok(dto);
    }
}
