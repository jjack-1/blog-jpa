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

/*
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
*/

    // locahost:8080?page=0
    public List<Board> findAll(int page, String keyword) {
        String sql;

        if (keyword.isBlank()) {
            sql = "select b from Board b where b.isPublic = true order by b.id desc";

        } else {
            sql = "select b from Board b where b.isPublic = true and b.title like :keyword order by b.id desc";
        }

        Query query = em.createQuery(sql, Board.class);
        if (!keyword.isBlank()) query.setParameter("keyword", "%" + keyword + "%");
        query.setFirstResult(page * 3);
        query.setMaxResults(3);

        return query.getResultList();
    }

    public List<Board> findAll(Integer userId, int page, String keyword) {
        String sql;
        if (keyword.isBlank()) {
            sql = "select b from Board b where b.isPublic = true or b.user.id = :userId order by b.id desc";
        } else {
            sql = "select b from Board b where b.isPublic = true or b.user.id = :userId and b.title like :keyword order by b.id desc";
        }

        Query query = em.createQuery(sql, Board.class);
        query.setParameter("userId", userId);
        if (!keyword.isBlank()) query.setParameter("keyword", "%" + keyword + "%");
        query.setFirstResult(page * 3);
        query.setMaxResults(3);

        return query.getResultList();
    }

    public Board findById(Integer id) {
        return em.find(Board.class, id); // em.find()는 PC에 있는 캐싱된 데이터를 먼저 찾는다
    }

    // inner join -> join
    // on b.user.id = u.id -> 생략 가능
    // left outer join -> left join
    // fk자리에는 Board에 있는 User객체를 넣어줘야 한다
    // fetch 를 작성해야 b 라고만 적었을 때 user 정보도 같이 프로젝션해서 보여준다
    public Board findByIdJoinUser(Integer id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);

        return (Board) query.getSingleResult();
    }

    public Board findByIdJoinUserAndReplies(Integer id) {
        Query query = em.createQuery("select b from Board b join fetch b.user u left join fetch b.replies r left join fetch r.user where b.id = :id order by r.id desc", Board.class);
        query.setParameter("id", id);

        return (Board) query.getSingleResult();
    }

    public void deleteById(Integer id) {
        Query query = em.createQuery("delete from Board b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // 로그인 x 4
    // 로그인 o
    // 로그인 - ssar 5
    // 로그인 - ssar 아니면 4
    public Long totalCount(String keyword) {
        String sql;
        if (!(keyword.isBlank())) {
            sql = "select count(b) from Board b where b.isPublic = true and b.title like :keyword";
        } else {
            sql = "select count(b) from Board b where b.isPublic = true";
        }
        Query query = em.createQuery(sql, Long.class);
        // keyword를 포함 : title like %keyword%
        if (!(keyword.isBlank())) query.setParameter("keyword", "%" + keyword + "%");

        return (Long) query.getSingleResult();
    }

    public Long totalCount(int userId, String keyword) {
        String sql;
        if (!(keyword.isBlank())) {
            sql = "select count(b) from Board b where b.isPublic = true or b.user.id = :userId and b.title like :keyword";
        } else {
            sql = "select count(b) from Board b where b.isPublic = true or b.user.id = :userId";
        }
        Query query = em.createQuery(sql, Long.class);
        // keyword를 포함 : title like %keyword%
        if (!(keyword.isBlank())) query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("userId", userId);

        return (Long) query.getSingleResult();
    }
}