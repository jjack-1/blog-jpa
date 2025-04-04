package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    /*
     * 1. createNativeQuery -> 기본 쿼리
     * 2. createQuery -> JPA가 제공해주는 객체지향 쿼리(JPQL)
     *      "select u from User u where u.username = :username"
     *      user_tb -> User 객체
     *      u -> 별칭
     * 3. createNamedQuery -> Query Method 함수 이름으로 쿼리 생성 x
     * 4. createEntityGraph -> x
     * */

    public void save(User user) {
        em.persist(user); // 2. user -> 영속 객체
        // 3. user -> db와 동기화
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(int id) {
        // id에 들어가는 값은 pk만 가능
        return em.find(User.class, id);
    }
}
