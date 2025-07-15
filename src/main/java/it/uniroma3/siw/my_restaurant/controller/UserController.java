package it.uniroma3.siw.my_restaurant.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalController globalController;

    @Autowired 
    private CredentialsService credentialsService;

    // @GetMapping("/AllUsers")
    // public String getUsers(Model model) {
    //     UserDetails userDetails = this.globalController.getUser();
    //     Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
    //     List<User> users = this.userService.getAllUsersExceptCurrent(credentials);

    //     List<Integer> numberReviews = new ArrayList<>();
    //     for (User user : users) {
    //         numberReviews.add(user.getReviews().size());
    //     }
        
    //     model.addAttribute("user", credentials);
    //     model.addAttribute("numberReviews", numberReviews);
    //     model.addAttribute("users", users);

    //     return "showUsers.html";
    // }

    // @PostMapping("/users/delete/{userId}")
    // public String postDeleteUser(@PathVariable("userId") Long id) {
    //     this.userService.deleteUser(id);
    //     return "redirect:/AllUsers";
    // }

    // @PostMapping("delete/currentUser")
    // public String postDeleteCurrentUser(HttpServletRequest request) {
    //     UserDetails userDetails = this.globalController.getUser();
    //     Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

    //     this.credentialsService.deleteUser(credentials.getUsername());

    //     request.getSession().invalidate();
    //     SecurityContextHolder.clearContext();

    //     return "redirect:/";
    // }

}
