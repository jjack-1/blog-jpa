package shop.mtcoding.blog.love;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.user.User;

@Service
@RequiredArgsConstructor
public class LoveService {
    private final LoveRepository loveRepository;

    @Transactional
    public void 좋아요(LoveRequest.SaveDTO saveDTO, User sessionUser) {
        Love love = saveDTO.toEntity(sessionUser);

        Integer loveId = loveRepository.save(love);

        Long loveCount = loveRepository.findByBoardIdCount(saveDTO.getBoardId());


    }
}
