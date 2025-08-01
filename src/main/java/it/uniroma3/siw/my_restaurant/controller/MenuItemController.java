package it.uniroma3.siw.my_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.my_restaurant.controller.validator.MenuItemValidator;
import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.MenuItem;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.MenuItemService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemValidator menuItemValidator;

    @Autowired
    private GlobalController globalController;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/admin/addMenuItemLunch")
    public String getAddMenuItemLunch(@RequestParam("category") String category, Model model) {
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(category.trim());
        model.addAttribute("menuItem", menuItem);
        return "/admin/formMenuItemLunch.html";
    }

    @PostMapping("/admin/addMenuItemLunchSuccess")
    public String postAddMenuItemLunch(@Valid @ModelAttribute("menuItem") MenuItem menuItem, BindingResult bindingResult, Model model) {
        menuItem.setMeal(MenuItem.LUNCH);
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

        this.menuItemValidator.validate(menuItem, bindingResult);

        if(!bindingResult.hasErrors()) {
            menuItem.setUser(credentials.getUser());
            this.menuItemService.save(menuItem);

            model.addAttribute("meal", MenuItem.LUNCH);
            return "/admin/addMenuItemSuccessful.html";
        }

        return "/admin/formMenuItemLunch.html";
    }

    @GetMapping("/admin/addMenuItemDinner")
    public String getAddMenuItemDinner(@RequestParam("category") String category, Model model) {
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

        MenuItem menuItem = new MenuItem();
        menuItem.setCategory(category.trim());
        model.addAttribute("menuItem", menuItem);
        return "/admin/formMenuItemDinner.html";
    }

    @PostMapping("/admin/addMenuItemDinnerSuccess")
    public String postAddMenuItemDinner(@Valid @ModelAttribute("menuItem") MenuItem menuItem, BindingResult bindingResult, Model model) {
        menuItem.setMeal(MenuItem.DINNER);
    
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

        this.menuItemValidator.validate(menuItem, bindingResult);

        if(!bindingResult.hasErrors()) {
            menuItem.setUser(credentials.getUser());
            this.menuItemService.save(menuItem);

            model.addAttribute("meal", MenuItem.DINNER);
            return "/admin/addMenuItemSuccessful.html";
        }
       
        return "/admin/formMenuItemDinner.html";
    }
    
    @GetMapping("/showMenuLunch")
    public String getMenuLunch(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("loggedUser", null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("loggedUser", credentials);
		}

        model.addAttribute("menuItems", this.menuItemService.findByMeal(MenuItem.LUNCH)); 
        return "showMenuLunch.html";
    }

    @GetMapping("/showMenuDinner")
    public String getMenuDinner(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("loggedUser", null);
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("loggedUser", credentials);
		}

        model.addAttribute("menuItems", this.menuItemService.findByMeal(MenuItem.DINNER)); 
        return "showMenuDinner.html";
    }
    
    @PostMapping("/admin/deleteMenuLunch")
    public String postDeleteMenuLunch() {
        this.menuItemService.deleteByMeal(MenuItem.LUNCH);
        return "redirect:/";
    }
    
    @PostMapping("/admin/deleteMenuDinner")
    public String postDeleteMenuDinner() {
        this.menuItemService.deleteByMeal(MenuItem.DINNER);
        return "redirect:/";
    }
    
}
