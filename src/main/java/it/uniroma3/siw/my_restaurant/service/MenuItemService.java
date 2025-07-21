package it.uniroma3.siw.my_restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.my_restaurant.model.MenuItem;
import it.uniroma3.siw.my_restaurant.repository.MenuItemRepository;
import jakarta.transaction.Transactional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return (List<MenuItem>)this.menuItemRepository.findAll();
    }

    public List<MenuItem> findByMeal(String meal) {
        return this.menuItemRepository.findByMeal(meal);
    }

    @Transactional
    public void save(MenuItem menuItem){
        this.menuItemRepository.save(menuItem);
    }

    @Transactional
    public void deleteByMeal(String meal) {
        this.menuItemRepository.deleteByMeal(meal);
    }
}
