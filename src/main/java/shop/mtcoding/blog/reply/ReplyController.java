package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
}
