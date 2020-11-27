package springbootproject.springboot.services;

import springbootproject.springboot.entities.Brand;
import springbootproject.springboot.entities.Category;
import springbootproject.springboot.entities.ShopItem;

import java.util.List;

public interface ShopItemService {

    ShopItem addShopItem(ShopItem shopItem);
    List<ShopItem> getAllShopItems();
    List<ShopItem> getTopShopItems();
    ShopItem getShopItem(int id);
    void deleteShopItem(ShopItem shopItem);
    ShopItem saveShopItem(ShopItem shopItem);

    List<Brand> getBrands();
    Brand addBrand(Brand brand);
    Brand saveBrand(Brand brand);
    Brand getBrand(int id);

    List<Category> getCategories();
    Category getCategory(int id);
    Category addCategory(Category category);
    Category saveCategory(Category category);


    List<ShopItem> getFilteredShopItems(String name, int brandId, int category_id, int priceFrom, int priceTo, int stars, String order);

}
