package it.uniroma3.siw.my_restaurant.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.my_restaurant.model.Restaurant;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{

}
