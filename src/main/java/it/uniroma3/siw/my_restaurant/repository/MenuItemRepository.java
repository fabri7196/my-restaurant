package it.uniroma3.siw.my_restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.my_restaurant.model.MenuItem;
import java.util.List;


public interface MenuItemRepository extends CrudRepository<MenuItem, Long>{

    List<MenuItem> findByMeal(String meal);

    void deleteByMeal(String meal);

}

