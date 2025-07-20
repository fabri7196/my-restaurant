package it.uniroma3.siw.my_restaurant.controller.validator;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.my_restaurant.controller.GlobalController;
import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.Reservation;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;
import it.uniroma3.siw.my_restaurant.service.ReservationService;

@Component
public class ReservationValidator implements Validator {

    public static final int MAX_ORARIO_PRANZO = 14;
    public static final int MIN_ORARIO_PRANZO = 12;

    final int MAX_ORARIO_CENA = 22;
    final int MIN_ORARIO_CENA = 18;

    public static final int CAPIENZA_MAX_ESTERNA = 30;
	public static final int CAPIENZA_MAX_INTERNA = 30;

    @Autowired
	private ReservationService reservationService;

    @Autowired
    private GlobalController globalController;

    @Autowired
    private CredentialsService credentialsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Reservation.class.equals(clazz);
    }

    @Override
	public void validate(Object target, Errors errors) {

		Reservation reservation = (Reservation) target;
        LocalDate dateOfReservation = reservation.getDateOfReservation();

        if (dateOfReservation.isEqual(LocalDate.now()) && reservation.getTime().isBefore(LocalTime.now()))
            errors.rejectValue("time", "reservation.beforeTime");

		if (reservation.getTime() != null) {
			if (!((reservation.getTime().getHour() <= MAX_ORARIO_PRANZO
					&& reservation.getTime().getHour() >= MIN_ORARIO_PRANZO)
					|| (reservation.getTime().getHour() <= MAX_ORARIO_CENA
							&& reservation.getTime().getHour() >= MIN_ORARIO_CENA))) {
				errors.rejectValue("time", "reservation.time");
			}
		}

        UserDetails userDetails = this.globalController.getUser();
        Credentials credentials = this.credentialsService.getCredentials(userDetails.getUsername());

        Long excludeId = reservation.getId();

		if (this.reservationService.alreadyExists(reservation, credentials.getUser(), excludeId) != null)
			errors.rejectValue("dateOfReservation", "reservation.duplicato");

        if(reservation.getPlace().equals(Reservation.ESTERNO_POSTO)) {
            if (!this.reservationService.isReservationPossible(dateOfReservation, reservation.getPlace(), reservation.getNumberOfPerson()))
                errors.rejectValue("numberOfPerson", "reservation.completeExtern");
        }
        else {
            if (!this.reservationService.isReservationPossible(dateOfReservation, reservation.getPlace(), reservation.getNumberOfPerson()))
                errors.rejectValue("numberOfPerson", "reservation.completeIntern");
        }

	}

}
