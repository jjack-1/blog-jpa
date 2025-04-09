package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.love.Love;
import shop.mtcoding.blog.love.LoveRepository;
import shop.mtcoding.blog.reply.ReplyRepository;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final LoveRepository loveRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        Board board = saveDTO.toEntity(sessionUser);
        boardRepository.save(board);
    }

    public List<Board> 목록보기(Integer userId) {
        return boardRepository.findAll(userId);
    }

    public BoardResponse.DetailDTO 상세보기(Integer id, Integer sessionUserId) {
        Board board = boardRepository.findByIdJoinUserAndReplies(id); // board 조회
        Love love = loveRepository.findByUserIdAndBoardId(sessionUserId, id); // board에 대한 love 조회
        Long loveCount = loveRepository.findByBoardIdCount(id); // board에 대한 love 개수 조회

        Boolean isLove = love == null ? false : true;
        Integer loveId = love == null ? null : love.getId();

        BoardResponse.DetailDTO detailDTO = new BoardResponse.DetailDTO(board, sessionUserId, isLove, loveCount.intValue(), loveId);

        return detailDTO;
    }
}
