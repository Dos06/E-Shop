//package springbootproject.springboot.entities;
//
//import lombok.Data;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Data
//@Embeddable
//public class ShopitemOrderId implements Serializable {
//    @Column(name = "shopitem_id")
//    private int shopitemId;
//
//    @Column(name = "order_id")
//    private int orderId;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ShopitemOrderId that = (ShopitemOrderId) o;
//        return shopitemId == that.shopitemId && orderId == that.orderId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(shopitemId, orderId);
//    }
//}
