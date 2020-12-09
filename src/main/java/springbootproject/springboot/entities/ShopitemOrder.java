package springbootproject.springboot.entities;

import javax.persistence.*;

@Entity
@Table(name = "shopitems_orders")
@IdClass(ShopitemOrderId.class)
public class ShopitemOrder {
    @Id
    @ManyToOne
    @JoinColumn(name = "shopitem_id", referencedColumnName = "id")
    private ShopItem shopItem;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "quantity")
    private int quantity;
}
