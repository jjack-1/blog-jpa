package shop.mtcoding.blog.reply;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {
    private final EntityManager em;

    public List<Reply> findAllByBoardId(Integer boardId) {
        Query query = em.createQuery("select r from Reply r join fetch r.user where r.board.id = :boardId", Reply.class);
        query.setParameter("boardId", boardId);

        return query.getResultList();
    }

    public Reply save(Reply reply) {
        em.persist(reply);
        return reply;
    }

    public void deleteById(Integer id) {
        Query query = em.createQuery("delete from Reply r where r.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Reply findById(Integer id) {
        return em.find(Reply.class, id);
    }
}
