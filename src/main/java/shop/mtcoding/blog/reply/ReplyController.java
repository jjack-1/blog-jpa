package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.error.ex.Exception401;
import shop.mtcoding.blog.user.User;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    @PostMapping("/reply/save")
    public String saveReply(ReplyRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        replyService.댓글등록(reqDTO, sessionUser.getId());

        return "redirect:/board/" + reqDTO.getBoardId();
    }

    @PostMapping("/reply/{id}/delete")
    public String deleteReply(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("인증이 필요합니다");

        Integer boardId = replyService.댓글삭제(id, sessionUser.getId());

        return "redirect:/board/" + boardId;
    }
}
