package it.uniroma3.siw.my_restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.my_restaurant.controller.validator.ReservationValidator;
import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.Reservation;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.ReservationService;
import jakarta.validation.Valid;

@Controller
public class ReservationController {

    @Autowired
    private GlobalController globalController;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationValidator reservationValidator;

    @GetMapping("/reservation")
	public String getFormReservation(Model model) {
        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		model.addAttribute("loggedUser", credentials);
		model.addAttribute("reservation", new Reservation());
		return "/user/formReservation.html";
	}

	@PostMapping("/reservation")
	public String postFormReservation(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult, Model model) {

        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		reservation.setUser(credentials.getUser());
        if (reservation.getPlace().equals(Reservation.INTERNO_POSTO)) 
			reservation.setPlace(Reservation.INTERNO_POSTO);
		else 
			reservation.setPlace(Reservation.ESTERNO_POSTO);

        this.reservationValidator.validate(reservation, bindingResult);

		if (!bindingResult.hasErrors()) {
            this.reservationService.save(reservation);
			return "reservationSuccessful.html";
		}

		model.addAttribute("loggedUser", credentials);
		return "/user/formReservation.html";
	}

	@GetMapping("/user/allReservation")
	public String getShowListAllReservationUser(Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		
		List<Reservation> allReservation = this.reservationService.findAllReservationsOfUser(credentials.getUser());
		model.addAttribute("reservationList", allReservation);
		model.addAttribute("loggedUser", credentials);
		return "/user/showReservation.html";
	}

	@PostMapping("/user/{id}/delete")
	public String postReservationDelete(@PathVariable("id") Long reservationId) {
		this.reservationService.delete(reservationId);
		return "redirect:/user/allReservation";
	}

	@GetMapping("/user/{id}/updateReservation")
	public String getUpdateReservationForm(@PathVariable("id") Long reservationId, Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		Reservation reservation = this.reservationService.findById(reservationId);

		if (!reservation.getUser().getId().equals(credentials.getUser().getId())) {
        	return "error/403"; 
    	}
		
		model.addAttribute("loggedUser", credentials);
		model.addAttribute("reservation", reservation);

		return "/user/updateReservation.html";
	}
	
	@PostMapping("user/{id}/updateReservation")
	public String postUpdateReservationForm(@Valid @ModelAttribute("reservation") Reservation updateReservation, 
				BindingResult bindingResult, @PathVariable("id") Long reservationId, Model model) {

		UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

        if (updateReservation.getPlace().equals(Reservation.INTERNO_POSTO)) 
			updateReservation.setPlace(Reservation.INTERNO_POSTO);
		else 
			updateReservation.setPlace(Reservation.ESTERNO_POSTO);

        this.reservationValidator.validate(updateReservation, bindingResult);

		if (!bindingResult.hasErrors()) {
			updateReservation.setUser(credentials.getUser());
            this.reservationService.update(updateReservation, reservationId);
			return "reservationSuccessful.html";
		}

		model.addAttribute("loggedUser", credentials);
		return "/user/updateReservation.html";
	}
    
}
