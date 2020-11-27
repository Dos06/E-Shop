package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Brand;
import springbootproject.springboot.entities.Category;
import springbootproject.springboot.entities.ShopItem;
import springbootproject.springboot.repositories.BrandRepository;
import springbootproject.springboot.repositories.CategoryRepository;
import springbootproject.springboot.repositories.ShopItemRepository;
import springbootproject.springboot.services.ShopItemService;

import java.util.List;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @Override
    public ShopItem addShopItem(ShopItem shopItem) {
        return shopItemRepository.save(shopItem);
    }

    @Override
    public List<ShopItem> getAllShopItems() {
        return shopItemRepository.findAllByAmountGreaterThanOrderByPriceDesc(0);
    }

    @Override
    public List<ShopItem> getTopShopItems() {
        return shopItemRepository.findAllByTop(true);
    }

    @Override
    public ShopItem getShopItem(int id) {
        return shopItemRepository.findByIdAndAmountGreaterThan(id, 0);
    }

    @Override
    public void deleteShopItem(ShopItem shopItem) {
        shopItemRepository.delete(shopItem);
    }

    @Override
    public ShopItem saveShopItem(ShopItem shopItem) {
        return shopItemRepository.save(shopItem);
    }





    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrand(int id) {
        return brandRepository.getOne(id);
    }



    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }










    @Override
    public List<ShopItem> getFilteredShopItems(String name, int brandId, int category_id, int priceFrom, int priceTo, int stars, String order) {
//        if (order.equals("asc"))
//            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceAsc(name, priceFrom, priceTo, stars, 0);
//        else if (order.equals("desc"))
//            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceDesc(name, priceFrom, priceTo, stars, 0);
//        else
//            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThan(name, priceFrom, priceTo, stars, 0);



        Brand brand = null;
        if (brandId > 0)
            brand = brandRepository.getOne(brandId);

        Category category = null;
        if (category_id > 0)
            category = categoryRepository.getOne(category_id);

        List<ShopItem> shopItems = null;
        if (order.equals("asc")) {
            if (brand == null && category == null)
                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceAsc(name, priceFrom, priceTo, stars, 0);
            else if (category != null && brand == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceAsc(name, priceFrom, priceTo, stars, 0);
                Category finalCategory = category;
                shopItems.removeIf(shopItem -> !shopItem.getCategories().contains(finalCategory));
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContainingOrderByPriceAsc(name, priceFrom, priceFrom, stars, 0, category);
            }
            else if (category == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceAsc(name, priceFrom, priceTo, stars, 0);
                shopItems.removeIf(shopItem -> shopItem.getBrand().getId() != brandId);
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandOrderByPriceAsc(name, priceFrom, priceFrom, stars, 0, brand);
            }
            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContainingOrderByPriceAsc(name, priceFrom, priceTo, stars, 0, brand, category);
        }
        else if (order.equals("desc")) {
            if (brand == null && category == null)
                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceDesc(name, priceFrom, priceTo, stars, 0);
            else if (category != null && brand == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceDesc(name, priceFrom, priceTo, stars, 0);
                Category finalCategory = category;
                shopItems.removeIf(shopItem -> !shopItem.getCategories().contains(finalCategory));
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContainingOrderByPriceDesc(name, priceFrom, priceFrom, stars, 0, category);
            }
            else if (category == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceDesc(name, priceFrom, priceTo, stars, 0);
                shopItems.removeIf(shopItem -> shopItem.getBrand().getId() != brandId);
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandOrderByPriceDesc(name, priceFrom, priceFrom, stars, 0, brand);
            }
            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContainingOrderByPriceDesc(name, priceFrom, priceTo, stars, 0, brand, category);
        }
        else {
            if (brand == null && category == null)
                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThan(name, priceFrom, priceTo, stars, 0);
            else if (category != null && brand == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThan(name, priceFrom, priceTo, stars, 0);
                Category finalCategory = category;
                shopItems.removeIf(shopItem -> !shopItem.getCategories().contains(finalCategory));
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContaining(name, priceFrom, priceFrom, stars, 0, category);
            }
            else if (category == null) {
                shopItems = shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThan(name, priceFrom, priceTo, stars, 0);
                shopItems.removeIf(shopItem -> shopItem.getBrand().getId() != brandId);
                return shopItems;
//                return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrand(name, priceFrom, priceFrom, stars, 0, brand);
            }
            return shopItemRepository.findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContaining(name, priceFrom, priceTo, stars, 0, brand, category);
        }// 00  01  10  11
    }

}
