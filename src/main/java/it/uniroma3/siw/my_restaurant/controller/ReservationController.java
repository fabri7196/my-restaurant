package it.uniroma3.siw.my_restaurant.controller;

import static it.uniroma3.siw.my_restaurant.model.Credentials.ADMIN_ROLE;

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
import it.uniroma3.siw.my_restaurant.model.User;
import it.uniroma3.siw.my_restaurant.model.Reservation;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.ReservationService;
import it.uniroma3.siw.my_restaurant.service.UserService;
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

	@Autowired
	private UserService userService;

	@GetMapping("/reservation")
	public String getFormReservation(Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		model.addAttribute("loggedUser", credentials);
		model.addAttribute("reservation", new Reservation());
		return "/formReservation.html";
	}

	@PostMapping("/reservation")
	public String postFormReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
			BindingResult bindingResult, Model model) {

		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

		reservation.setUser(credentials.getUser());
		if (reservation.getPlace().equals(Reservation.INTERNO_POSTO))
			reservation.setPlace(Reservation.INTERNO_POSTO);
		else
			reservation.setPlace(Reservation.ESTERNO_POSTO);

		this.reservationValidator.validate(reservation, bindingResult);

		if (!bindingResult.hasErrors()) {
			this.reservationService.save(reservation);
			return "/reservationSuccessful.html";
		}

		return "/formReservation.html";
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
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		Reservation reservation = this.reservationService.findById(reservationId);

		if (!reservation.getUser().getId().equals(credentials.getUser().getId())) {
			return "error/403";
		}

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

		return "/updateReservation.html";
	}

	@PostMapping("/user/{id}/updateReservation")
	public String postUpdateReservationForm(@Valid @ModelAttribute("reservation") Reservation updateReservation,
			BindingResult bindingResult, @PathVariable("id") Long reservationId, Model model) {

		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

		if (updateReservation.getPlace().equals(Reservation.INTERNO_POSTO))
			updateReservation.setPlace(Reservation.INTERNO_POSTO);
		else
			updateReservation.setPlace(Reservation.ESTERNO_POSTO);

		this.reservationValidator.validate(updateReservation, bindingResult);

		if (!bindingResult.hasErrors()) {
			updateReservation.setUser(credentials.getUser());
			this.reservationService.update(updateReservation, reservationId);
			return "/reservationUpdateSuccessful.html";
		}

		return "/updateReservation.html";
	}

	// _______ PARTE ADMIN ________

	@GetMapping("/admin/allReservation")
	public String getShowListAllReservations(Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		if (credentials.getRole().equals(ADMIN_ROLE)) {
			List<Reservation> allReservation = this.reservationService.findAllReservations();
			model.addAttribute("reservationList", allReservation);
			model.addAttribute("loggedUser", credentials);

			return "/admin/showAllReservation.html";
		}

		return "redirect:/login";
	}

	@PostMapping("/admin/allReservation/{id}/delete")
	public String postAllReservationDelete(@PathVariable("id") Long reservationId) {
		this.reservationService.delete(reservationId);
		return "redirect:/admin/allReservation";
	}

	@GetMapping("/admin/AllUsers/{id}/reservationUser")
	public String getAdminShowUserReservation(@PathVariable("id") Long userId, Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		User user = this.userService.getUser(userId);

		List<Reservation> reservationList = this.reservationService.findAllReservationsOfUser(user);

		model.addAttribute("loggedUser", credentials);
		model.addAttribute("user", user);
		model.addAttribute("reservationList", reservationList);

		return "/admin/showUserReservation.html";
	}

	@PostMapping("/admin/AllUsers/{uid}/reservationUser/{rid}/delete")
	public String postUserReservationDelete(@PathVariable("rid") Long reservationId, @PathVariable("uid") Long userId) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		if (this.reservationService.findById(reservationId).getUser().getId() == credentials.getId()) {
			this.reservationService.delete(reservationId);
			return "redirect:/admin/AllUsers/" + userId + "/reservationUser";
		}

		return "redirect:/";

	}

	@GetMapping("/admin/{id}/updateReservation")
	public String getUpdateReservationFormAdmin(@PathVariable("id") Long reservationId, Model model) {
		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

		Reservation reservation = this.reservationService.findById(reservationId);

		model.addAttribute("loggedUser", credentials);
		model.addAttribute("reservation", reservation);

		return "/updateReservation.html";
	}

	@PostMapping("/admin/{id}/updateReservation")
	public String postUpdateReservationFormAdmin(@Valid @ModelAttribute("reservation") Reservation updateReservation,
			BindingResult bindingResult, @PathVariable("id") Long reservationId, Model model) {

		UserDetails userDetails = this.globalController.getUser();
		Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("loggedUser", credentials);

		if (updateReservation.getPlace().equals(Reservation.INTERNO_POSTO))
			updateReservation.setPlace(Reservation.INTERNO_POSTO);
		else
			updateReservation.setPlace(Reservation.ESTERNO_POSTO);

		this.reservationValidator.validate(updateReservation, bindingResult);

		if (!bindingResult.hasErrors()) {
			updateReservation.setUser(credentials.getUser());
			this.reservationService.update(updateReservation, reservationId);
			return "/reservationUpdateSuccessful.html";
		}

		return "/updateReservation.html";
	}

}
