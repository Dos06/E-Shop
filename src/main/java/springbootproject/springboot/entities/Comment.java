package springbootproject.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "added_date")
    private LocalDateTime addedDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopItem shopItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
