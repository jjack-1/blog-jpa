package shop.mtcoding.blog.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.error.ex.Exception401;
import shop.mtcoding.blog._core.util.Resp;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletResponse response) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);

        if (loginDTO.getRememberMe() == null) {
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0); // 브라우저가 MaxAge가 0인 쿠키는 자동 삭제함
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("username", loginDTO.getUsername());
            cookie.setMaxAge(24 * 60 * 60 * 7); // MaxAge에 값을 넣으면 브라우저를 새로 켜도 유지됨
            response.addCookie(cookie);
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/check-username-available/{username}")
    public @ResponseBody Resp<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.유저네임중복체크(username);
        return Resp.ok(dto);
    }

    @GetMapping("/user/update-form")
    public String updateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        return "user/update-form"; // view resolver -> prefix 로 templates/가 되어 있다. subfix 로 .mustache가 되어 있다
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        User userPS = userService.회원정보수정(updateDTO, sessionUser.getId());

        // 세션 동기화
        session.setAttribute("sessionUser", userPS);

        return "redirect:/";
    }
}
