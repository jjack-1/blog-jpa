package shop.mtcoding.blog.love;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoveRepository {
    private final EntityManager em;

    public Love findByUserIdAndBoardId(Integer userId, Integer boardId) {
        Query query = em.createQuery("select lo from Love lo where lo.user.id = :userId and lo.board.id = :boardId", Love.class);
        query.setParameter("userId", userId);
        query.setParameter("boardId", boardId);

        try {
            return (Love) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Love> findByBoardId(Integer boardId) {
        Query query = em.createQuery("select lo from Love lo where lo.board.id = :boardId", Love.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }
}
