package shop.mtcoding.blog.love;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    // 게시글에 대한 좋아요 숫자만 return
    public Long findByBoardIdCount(Integer boardId) {
        Query query = em.createQuery("select count(lo) from Love lo where lo.board.id = :boardId");
        query.setParameter("boardId", boardId);

        return (Long) query.getSingleResult();
    }

    public Integer save(Love love) {
        em.persist(love);
        return love.getId();
    }
}
