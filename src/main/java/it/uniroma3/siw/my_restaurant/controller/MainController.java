package it.uniroma3.siw.my_restaurant.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.ReservationService;

@Controller
public class MainController {

    @Autowired
    private GlobalController globalController;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = this.globalController.getUser();
            Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
            model.addAttribute("loggedUser", credentials);

            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                String formattedDate = new SimpleDateFormat("EEE, d MMM yyyy").format(new Date());
                Long allReservationForToday = this.reservationService.countAllReservationsByDateOfReservation(LocalDate.now());
                model.addAttribute("allReservationForToday", allReservationForToday);
                model.addAttribute("date", formattedDate);
                return "/admin/dashboard.html";
            }
        }
        return "/user/dashboard.html";
    }

}
