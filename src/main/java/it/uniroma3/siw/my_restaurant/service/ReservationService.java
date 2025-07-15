package it.uniroma3.siw.my_restaurant.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.my_restaurant.model.User;
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

	// public void update(Prenotazione prenotazione, Long id) {
	// 	prenotazione.setId(id);
	// 	prenotazioneRepository.save(prenotazione);		
	// }
	
	// VERIFICARE SE FUNZIONA 
	public Reservation alreadyExists(Reservation reservation, User loggedUser) {
		return this.reservationRepository.findByDateOfReservationAndUser(reservation.getDateOfReservation(), loggedUser);
	}

	public Reservation findById(Long id) {
		return this.reservationRepository.findById(id).get();
	}

	public List<Reservation> getAllReservations() {
		return this.reservationRepository.findAll();
	}
	
	public Long countAllReservations() {
		return this.reservationRepository.count();
	}

	public List<Reservation> getAllReservationsOfUser(User user) {
		return this.reservationRepository.findByUser(user);	
	}

	public boolean isReservationPossible(LocalDate date, String place, int numberOfPeopleToAdd) {
    	int currentReservations = reservationRepository.getTotalPeopleReservedByDateAndPlace(date, place);
    	return (currentReservations + numberOfPeopleToAdd) <= 30;
	}	

}
