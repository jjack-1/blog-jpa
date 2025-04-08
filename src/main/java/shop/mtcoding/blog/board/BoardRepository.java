package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
        Query query = em.createNativeQuery("""
                    SELECT
                        bt.id,
                        bt.title,
                        bt.content,
                        bt.is_public,
                        CASE
                            WHEN bt.user_id = ? THEN true
                            ELSE false
                        END AS is_owner,
                        ut.username,
                        bt.created_at,
                        CASE
                            WHEN MAX(CASE WHEN lt.user_id = ? THEN 1 ELSE 0 END) = 1
                            THEN true
                            ELSE false
                        END AS is_love,
                        COUNT(lt.id) AS love_count
                    FROM board_tb bt
                    INNER JOIN user_tb ut
                        ON bt.user_id = ut.id
                    LEFT OUTER JOIN love_tb lt
                        ON bt.id = lt.board_id
                    WHERE bt.id = ?
                    GROUP BY bt.id;
                """);
        query.setParameter(1, sessionUserId);
        query.setParameter(2, sessionUserId);
        query.setParameter(3, id);

        Object[] objects = (Object[]) query.getSingleResult();

        return new BoardResponse.DetailDTO(
                (Integer) objects[0],
                (String) objects[1],
                (String) objects[2],
                (Boolean) objects[3],
                (Boolean) objects[4],
                (String) objects[5],
                (Timestamp) objects[6],
                (Boolean) objects[7],
                ((Long) objects[8]).intValue()
        );
    }
}