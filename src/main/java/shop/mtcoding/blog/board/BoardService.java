package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        Board board = saveDTO.toEntity(sessionUser);
        boardRepository.save(board);
    }

    public List<Board> 목록보기(Integer userId) {
        return boardRepository.findAll(userId);
    }
    
    public BoardResponse.DetailDTO 상세보기(Integer id, Integer sessionUserId) {
        Board board = boardRepository.findByIdWithUser(id);

        BoardResponse.DetailDTO detailDTO = new BoardResponse.DetailDTO(board, sessionUserId);

        return detailDTO;
    }
}
