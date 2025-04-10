package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public void 댓글등록(ReplyRequest.SaveDTO reqDTO, Integer sessionUserId) {
        replyRepository.save(reqDTO.toEntity(sessionUserId));
    }

    @Transactional
    public Integer 댓글삭제(Integer id, Integer sessionUserId) {
        Reply replyPS = replyRepository.findById(id);
        if (replyPS == null) throw new RuntimeException("해당 댓글이 없습니다");

        if (!(replyPS.getUser().getId().equals(sessionUserId))) throw new RuntimeException("니가 작성한 댓글이 아니다");

        replyRepository.deleteById(id); // 삭제 쿼리를 날리기만 한다

        return replyPS.getBoard().getId(); // pc 객체는 남아 있다
    }
}
