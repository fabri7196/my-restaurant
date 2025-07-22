package it.uniroma3.siw.my_restaurant.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.my_restaurant.model.MenuItem;
import it.uniroma3.siw.my_restaurant.service.MenuItemService;

@Component
public class MenuItemValidator implements Validator{

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MenuItem.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MenuItem menuItem = (MenuItem)target;

        //PIATTO E' UGUALE SE HA STESSO NOME E STESSO PASTO
        if(this.menuItemService.findByMealAndName(menuItem.getMeal(), menuItem.getName()) != null) {
            errors.rejectValue("name", "menuItem.duplicato");
        }
    }
    
}
