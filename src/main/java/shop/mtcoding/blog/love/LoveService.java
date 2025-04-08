package shop.mtcoding.blog.love;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.board.BoardRepository;
import shop.mtcoding.blog.user.User;

@Service
@RequiredArgsConstructor
public class LoveService {
    private final LoveRepository loveRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void 좋아요(LoveRequest.SaveDTO saveDTO, User sessionUser) {
        // 1. 영속화된 board 객체를 가져와야 한다
//        Board boardPS = boardRepository.findById(saveDTO.getBoardId());
        Love love = saveDTO.toEntity(sessionUser);

        loveRepository.save(love);
    }
}
