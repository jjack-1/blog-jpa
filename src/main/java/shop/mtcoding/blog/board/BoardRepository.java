package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public List<Board> findAll(Integer userId) {
        String s1 = "select b from Board b where b.isPublic = true or b.user.id = :userId order by b.id desc";
        String s2 = "select b from Board b where b.isPublic = true order by b.id desc";

        Query query;
        if (userId == null) {
            query = em.createQuery(s2, Board.class);
        } else {
            query = em.createQuery(s1, Board.class);
            query.setParameter("userId", userId);
        }

        return query.getResultList();
    }

    public Board findById(Integer id) {
        return em.find(Board.class, id); // em.find()는 PC에 있는 캐싱된 데이터를 먼저 찾는다
    }

    public Board findByIdJoinUser(Integer id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    public BoardResponse.DetailDTO findDetail(Integer id, Integer sessionUserId) {
        Query query = em.createQuery("""
                        SELECT new shop.mtcoding.blog.board.BoardResponse$DetailDTO(
                            b.id,
                            b.title,
                            b.content,
                            b.isPublic,
                            CASE WHEN b.user.id = :userId THEN true ELSE false END,
                            b.user.username,
                            b.createdAt,
                            CASE WHEN MAX(CASE WHEN l.user.id = :userId THEN 1 ELSE 0 END) = 1 THEN true ELSE false END,
                            COUNT(l.id)
                        )
                        FROM Board b
                        LEFT JOIN Love l on b.id = l.board.id
                        WHERE b.id = :boardId
                        GROUP BY b.id, b.title, b.content, b.isPublic, b.user.id, b.user.username, b.createdAt
                """);
        query.setParameter("boardId", id);
        query.setParameter("userId", sessionUserId);
        return (BoardResponse.DetailDTO) query.getSingleResult();
    }
}