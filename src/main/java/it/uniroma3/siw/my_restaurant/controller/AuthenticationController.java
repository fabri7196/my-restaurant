package it.uniroma3.siw.my_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.my_restaurant.controller.validator.CredentialsValidator;
import it.uniroma3.siw.my_restaurant.controller.validator.UserValidator;
import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.User;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private UserValidator userValidator;
	
	@GetMapping(value = "/register") 
	public String getShowRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser.html";
	}

	@PostMapping(value = "/register")
    public String postRegisterUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
		
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		this.userValidator.validate(user, userBindingResult);
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			this.userService.saveUser(user);
            credentials.setUser(user);
            this.credentialsService.saveCredentials(credentials, Credentials.DEFAULT_ROLE);
            model.addAttribute("user", user);
            return "registrationSuccessful.html";
        }
        return "formRegisterUser.html";
    }
	
	@GetMapping(value = "/login") 
	public String getShowLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/") 
	public String getIndex(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("loggedUser", null);
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("loggedUser", credentials);
		}

        return "index.html";
	}
		
    // @GetMapping(value = "/success")
    // public String getDefaultAfterLogin(Model model) {
        
    // 	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // 	Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
    // 	model.addAttribute("loggedUser", credentials);
        
	// 	return "index.html";
    // }
	
}
