package springbootproject.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shopitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "stars")
    private int stars;

    @Column(name = "pictureurl", columnDefinition = "TEXT")
    private String pictureURL;

    @Column(name = "pictureurllrg", columnDefinition = "TEXT")
    private String pictureURLlrg;

    @Column(name = "top", columnDefinition = "tinyint(1) default 0")
    private boolean top;

//    @Column(name = "addeddate")
//    @Temporal(TemporalType.DATE)
//    @CreationTimestamp
//    private LocalDate addedDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

    @OneToMany(mappedBy = "shopItem")
    private List<ShopitemOrder> orderAssociation;

}
