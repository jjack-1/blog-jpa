package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardRepository.class) // BoardRepository
@DataJpaTest // EntityManager, PC
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void findDetail_test() {
        //given
        Integer id = 4;
        Integer sessionUserId = 1;

        // when
        Object[] objects = boardRepository.findDetail(id, sessionUserId);

        // eye
        for (Object object : objects) {
            System.out.println(object);
        }
    }
}