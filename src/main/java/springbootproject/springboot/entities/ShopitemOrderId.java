package springbootproject.springboot.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopitemOrderId implements Serializable {
    private int shopItem;
    private int order;
}
