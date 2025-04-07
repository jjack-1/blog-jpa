package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    @CreationTimestamp // 자동 now() 들어감
    private Timestamp createdAt;

    @Builder // 빌터패턴의 메서드를 만들어줌 // 조건 모든 필드값의 생성자를 1개 만들고 그 위에 @Builder를 추가하면 된다
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    // 회원정보 수정 setter
    public void update(String password, String email) {
        this.password = password;
        this.email = email;
    }
}