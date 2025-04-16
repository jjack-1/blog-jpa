package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.user.User;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public String list(HttpServletRequest request, @RequestParam(required = false, value = "page", defaultValue = "0") Integer page) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            request.setAttribute("model", boardService.목록보기(null, page));
        } else {
            request.setAttribute("model", boardService.목록보기(sessionUser.getId(), page));
        }

        return "board/list";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @PostMapping("/board/save")
    public String save(@Valid BoardRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

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

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Board board = boardService.수정상세보기(id, sessionUser.getId());

        request.setAttribute("model", board);

        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") Integer id, @Valid BoardRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.글수정(id, reqDTO, sessionUser.getId());

        return "redirect:/board/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {

        boardService.글삭제(id);

        return "redirect:/";
    }
}
