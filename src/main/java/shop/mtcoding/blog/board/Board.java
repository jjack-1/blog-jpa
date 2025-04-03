package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 설정
    // EAGER -> 처음 조회할 때 바로 join함.
    // LAZY -> 처음 조회할 때 board만 가져옴, 나중에 getUser()하면 그 때 조회를 한번 더함
    private User user;

    @CreationTimestamp // 자동 now() 들어감
    private Timestamp createdAt;

    @Builder // 빌터패턴의 메서드를 만들어줌 // 조건 모든 필드값의 생성자를 1개 만들고 그 위에 @Builder를 추가하면 된다
    public Board(Integer id, String title, String content, Boolean isPublic, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.user = user;
        this.createdAt = createdAt;
    }
}