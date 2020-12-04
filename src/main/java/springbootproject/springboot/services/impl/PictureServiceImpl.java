package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Picture;
import springbootproject.springboot.entities.ShopItem;
import springbootproject.springboot.repositories.PictureRepository;
import springbootproject.springboot.services.PictureService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture addPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Picture getPicture(int id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public void deletePicture(Picture picture) {
        pictureRepository.delete(picture);
    }

    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> getPicturesByShopItem(ShopItem shopItem) {
        return pictureRepository.findAllByShopItem(shopItem);
    }
}
