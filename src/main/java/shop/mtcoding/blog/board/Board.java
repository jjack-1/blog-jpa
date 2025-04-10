package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ToString
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

    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정 -> ORM 하려고 EAGER -> fk에 들어간 오브젝트를 바로 연관관계 맵핑을 해서 select를 여러 번 한다 , LAZY -> 무조건 LAZY를 사용한다. 연관관계 맵핑을 하지 않는다
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // mappedBy -> fk 의 주인인 reply의 필드 이름을 적어야 한다
    private List<Reply> replies = new ArrayList<Reply>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder // 빌더 어노테이션은 컬렉션을 지원하지 않는다
    public Board(Integer id, String title, String content, Boolean isPublic, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
        this.user = user;
        this.createdAt = createdAt;
    }

    // 게시글 수정 setter
    public void update(String title, String content, String isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = "on".equals(isPublic);
    }
}