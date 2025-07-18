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
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.User;
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

    @GetMapping("/userProfile")
    public String getUserProfile(Model model) {
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        model.addAttribute("loggedUser", credentials);

        return "userProfile.html";
    }

    @GetMapping("/admin/allUsers")
    public String getUserList(Model model) {
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
        List<User> users = this.userService.getAllUsersExceptCurrent(credentials);
        
        model.addAttribute("loggedUser", credentials);
        model.addAttribute("users", users);
        return "/admin/showAllUsers.html";
    }

    @PostMapping("/admin/allUsers/{userId}/delete")
    public String postDeleteUser(@PathVariable("userId") Long id) {
    this.userService.deleteUser(id);
    return "redirect:/admin/allUsers";
    }

    // @GetMapping("/AllUsers")
    // public String getUsers(Model model) {
    // UserDetails userDetails = this.globalController.getUser();
    // Credentials credentials =
    // this.credentialsService.getCredentials(userDetails.getUsername());
    // List<User> users = this.userService.getAllUsersExceptCurrent(credentials);

    // List<Integer> numberReviews = new ArrayList<>();
    // for (User user : users) {
    // numberReviews.add(user.getReviews().size());
    // }

    // model.addAttribute("user", credentials);
    // model.addAttribute("numberReviews", numberReviews);
    // model.addAttribute("users", users);

    // return "showUsers.html";
    // }

    // @PostMapping("delete/currentUser")
    // public String postDeleteCurrentUser(HttpServletRequest request) {
    // UserDetails userDetails = this.globalController.getUser();
    // Credentials credentials =
    // this.credentialsService.getCredentials(userDetails.getUsername());

    // this.credentialsService.deleteUser(credentials.getUsername());

    // request.getSession().invalidate();
    // SecurityContextHolder.clearContext();

    // return "redirect:/";
    // }

}
