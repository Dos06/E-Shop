package springbootproject.springboot.services;

import springbootproject.springboot.entities.Country;
import springbootproject.springboot.entities.Brand;

import java.util.List;

public interface BrandService {

    Brand addBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrand(int id);
    void deleteBrand(Brand brand);
    Brand saveBrand(Brand brand);
    List<Country> getCountries();
    Country addCountry(Country country);
    Country saveCountry(Country country);
    Country getCountry(int id);

}
