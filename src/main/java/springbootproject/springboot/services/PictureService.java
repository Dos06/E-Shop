package springbootproject.springboot.services;

import springbootproject.springboot.entities.Picture;
import springbootproject.springboot.entities.ShopItem;

import java.util.List;

public interface PictureService {
    Picture addPicture(Picture picture);
    List<Picture> getAllPictures();
    Picture getPicture(int id);
    void deletePicture(Picture picture);
    Picture savePicture(Picture picture);

    List<Picture> getPicturesByShopItem(ShopItem shopItem);
}
