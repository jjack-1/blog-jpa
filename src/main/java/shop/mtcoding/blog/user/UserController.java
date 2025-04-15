package shop.mtcoding.blog.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.error.anno.MyAfter;
import shop.mtcoding.blog._core.error.anno.MyAround;
import shop.mtcoding.blog._core.error.anno.MyBefore;
import shop.mtcoding.blog._core.error.ex.Exception400;
import shop.mtcoding.blog._core.util.Resp;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @MyAround
    @GetMapping("/v2/around")
    public @ResponseBody String around() {
        return "around";
    }

    @MyBefore
    @GetMapping("/join-form")
    public String joinForm() {
        System.out.println("join-form 호출됨");
        return "user/join-form";
    }

    @MyAfter
    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) { // @Valid <- 어노테이션이 있어야 DTO 오브젝트 내부의 @검증 어노테이션이 실행됨, 검증 문제가 생기면 Errors 로 넘겨준다. 이제 DTO의 책임은 유효성 검사다. @Valid 어노테이션 바로 뒤에 Errors를 붙여야 동작한다
/*
        // 유효성 검사
        boolean r1 = Pattern.matches("^[a-zA-Z0-9]{2,20}$", joinDTO.getUsername());
        boolean r2 = Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()])[a-zA-Z\\d!@#$%^&*()]{6,20}$", joinDTO.getPassword());
        boolean r3 = Pattern.matches("^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$", joinDTO.getEmail());

        if (!r1) throw new Exception400("유저네임은 2-20자이며, 영어와 숫자만 가능합니다");
        if (!r2) throw new Exception400("패스워드는 6-20자이며, 특수문자,영어 대문자,소문자, 숫자가 포함되어야 하며, 공백이 있을 수 없습니다");
        if (!r3) throw new Exception400("이메일 형식에 맞게 적어주세요");
*/
        System.out.println("join 호출됨");
        if (errors.hasErrors()) {
            List<FieldError> fErrors = errors.getFieldErrors();
            for (FieldError fError : fErrors) {
                throw new Exception400(fError.getField() + ":" + fError.getDefaultMessage());
            }
        }

        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO loginDTO, Errors errors, HttpServletResponse response) {
        if (errors.hasErrors()) {
            List<FieldError> fErrors = errors.getFieldErrors();
            for (FieldError fError : fErrors) {
                throw new Exception400(fError.getField() + ":" + fError.getDefaultMessage());
            }
        }

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

    @GetMapping("/api/check-username-available/{username}")
    public @ResponseBody Resp<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.유저네임중복체크(username);
        return Resp.ok(dto);
    }

    @GetMapping("/user/update-form")
    public String updateForm() {
        return "user/update-form"; // view resolver -> prefix 로 templates/가 되어 있다. subfix 로 .mustache가 되어 있다
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        User userPS = userService.회원정보수정(updateDTO, sessionUser.getId());

        // 세션 동기화
        session.setAttribute("sessionUser", userPS);

        return "redirect:/";
    }
}
