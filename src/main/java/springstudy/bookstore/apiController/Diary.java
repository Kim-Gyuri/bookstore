package springstudy.bookstore.apiController;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diary_id;  // 일기 pk

    @Column(length = 1000)
    private String content; // 일기 내용

    @Column
    private String imageUrl;

    public Diary(Long diary_id, String content, String imageUrl) {
        this.diary_id = diary_id;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}