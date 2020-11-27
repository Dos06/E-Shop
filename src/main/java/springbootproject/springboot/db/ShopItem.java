package springbootproject.springboot.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopItem {
    private int id;
    private String name, description;
    private int price, amount, stars;
    private String pictureURL;
}
