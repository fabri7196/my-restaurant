package it.uniroma3.siw.my_restaurant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.my_restaurant.model.User;
import it.uniroma3.siw.my_restaurant.controller.validator.ReservationValidator;
import it.uniroma3.siw.my_restaurant.model.Reservation;
import it.uniroma3.siw.my_restaurant.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	public void save(Reservation reservation) {
		this.reservationRepository.save(reservation);		
	}

	public void delete(Long id) {
		this.reservationRepository.deleteById(id);
	}

	public void update(Reservation reservation, Long id) {
		reservation.setId(id);
		this.reservationRepository.save(reservation);	
	}
	
	public Reservation alreadyExists(Reservation reservation, User loggedUser, Long excludeId) {
		Reservation reservationOfUser = this.reservationRepository.findByDateOfReservationAndUser(reservation.getDateOfReservation(), loggedUser);
    
		if(reservationOfUser == null)
			return null;
        
		if (!reservationOfUser.getId().equals(excludeId)) {
            return reservationOfUser;
        }
    	return null;

	}

	public Reservation findById(Long id) {
		return this.reservationRepository.findById(id).get();
	}

	public List<Reservation> findAllReservations() {
		return this.reservationRepository.findAll();
	}

	public Long countAllReservationsByDateOfReservation(LocalDate dateOfReservation) {
		List<Reservation> allReservationDay = this.reservationRepository.findByDateOfReservation(dateOfReservation);
		if (allReservationDay == null || allReservationDay.isEmpty())
			return Long.valueOf(0);
		
		return Long.valueOf(allReservationDay.size());
	}

	public List<Reservation> findAllReservationsOfUser(User user) {
		return this.reservationRepository.findByUser(user);	
	}
	
	public Long countAllReservations() {
		return this.reservationRepository.count();
	}

	public boolean isReservationPossible(LocalDate date, String place, int numberOfPeopleToAdd) {
    	int currentReservations = reservationRepository.getTotalPeopleReservedByDateAndPlace(date, place);
    	return (currentReservations + numberOfPeopleToAdd) <= ReservationValidator.CAPIENZA_MAX_ESTERNA;
	}	

}
