package springbootproject.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootproject.springboot.entities.Brand;
import springbootproject.springboot.entities.Category;
import springbootproject.springboot.entities.ShopItem;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {

    List<ShopItem> findAllByAmountGreaterThanOrderByPriceDesc(int amount);
    List<ShopItem> findAllByTop(boolean top);
    ShopItem findByIdAndAmountGreaterThan(int id, int amount);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThan(String name,
                                                                                                      int priceFrom,
                                                                                                      int priceTo,
                                                                                                      int stars,
                                                                                                      int amount);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceAsc(String name,
                                                                                                                  int priceFrom,
                                                                                                                  int priceTo,
                                                                                                                  int stars,
                                                                                                                  int amount);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanOrderByPriceDesc(String name,
                                                                                                                      int priceFrom,
                                                                                                                      int priceTo,
                                                                                                                      int stars,
                                                                                                                      int amount);









    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrand(String name,
                                                                                                                 int priceFrom,
                                                                                                                 int priceTo,
                                                                                                                 int stars,
                                                                                                                 int amount,
                                                                                                                 Brand brand);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandOrderByPriceAsc(String name,
                                                                                                                            int priceFrom,
                                                                                                                            int priceTo,
                                                                                                                            int stars,
                                                                                                                            int amount,
                                                                                                                            Brand brand);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandOrderByPriceDesc(String name,
                                                                                                                             int priceFrom,
                                                                                                                             int priceTo,
                                                                                                                             int stars,
                                                                                                                             int amount,
                                                                                                                             Brand brand);











    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContaining(String name,
                                                                                                      int priceFrom,
                                                                                                      int priceTo,
                                                                                                      int stars,
                                                                                                      int amount,
                                                                                                      Category category);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContainingOrderByPriceAsc(String name,
                                                                                                                  int priceFrom,
                                                                                                                  int priceTo,
                                                                                                                  int stars,
                                                                                                                  int amount,
                                                                                                                  Category category);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndCategoriesContainingOrderByPriceDesc(String name,
                                                                                                                      int priceFrom,
                                                                                                                      int priceTo,
                                                                                                                      int stars,
                                                                                                                      int amount,
                                                                                                                      Category category);














    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContaining(String name,
                                                                                                      int priceFrom,
                                                                                                      int priceTo,
                                                                                                      int stars,
                                                                                                      int amount,
                                                                                                      Brand brand,
                                                                                                      Category category);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContainingOrderByPriceAsc(String name,
                                                                                                                  int priceFrom,
                                                                                                                  int priceTo,
                                                                                                                  int stars,
                                                                                                                  int amount,
                                                                                                                  Brand brand,
                                                                                                                  Category category);

    List<ShopItem> findAllByNameContainingAndPriceBetweenAndStarsGreaterThanEqualAndAmountGreaterThanAndBrandAndCategoriesContainingOrderByPriceDesc(String name,
                                                                                                                      int priceFrom,
                                                                                                                      int priceTo,
                                                                                                                      int stars,
                                                                                                                      int amount,
                                                                                                                      Brand brand,
                                                                                                                      Category category);

}
