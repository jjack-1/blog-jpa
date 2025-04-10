package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blog._core.error.ex.Exception401;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            List<Board> boardList = boardService.목록보기(null);
            request.setAttribute("models", boardList);
        } else {
            List<Board> boardList = boardService.목록보기(sessionUser.getId());
            request.setAttribute("models", boardList);
        }

        return "board/list";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        boardService.글쓰기(reqDTO, sessionUser);

        return "redirect:/";
    }

    @GetMapping("/v2/board/{id}")
    public @ResponseBody BoardResponse.DetailDTO v2Detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Integer sessionUserId = 1;

        BoardResponse.DetailDTO detailDTO = boardService.상세보기(id, sessionUserId);

        request.setAttribute("model", detailDTO);

        return detailDTO;
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();

        BoardResponse.DetailDTO detailDTO = boardService.상세보기(id, sessionUserId);

        request.setAttribute("model", detailDTO);

        return "board/detail";
    }
}
