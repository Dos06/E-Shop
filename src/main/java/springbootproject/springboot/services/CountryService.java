package springbootproject.springboot.services;

import springbootproject.springboot.entities.Country;

import java.util.List;

public interface CountryService {

    Country addCountry(Country country);
    List<Country> getAllCountries();
    Country getCountry(int id);
    void deleteCountry(Country country);
    Country saveCountry(Country country);
    
}
