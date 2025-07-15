package it.uniroma3.siw.my_restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.my_restaurant.model.Reservation;
import it.uniroma3.siw.my_restaurant.model.User;

import java.util.List;
import java.time.LocalDate;


public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    public List<Reservation> findByDateOfReservation(LocalDate dateOfReservation);

    public List<Reservation> findByUser(User user);

    public List<Reservation> findAll();

    @Query("SELECT r FROM Reservation r WHERE r.dateOfReservation = :dateOfReservation AND r.user = :user")
    public Reservation findByDateOfReservationAndUser(@Param("dateOfReservation") LocalDate dateOfReservation, @Param("user") User user);

    @Query("SELECT COALESCE(SUM(r.numberOfPerson), 0) FROM Reservation r WHERE r.dateOfReservation = :date AND r.place = :place")
    int getTotalPeopleReservedByDateAndPlace(@Param("date") LocalDate date, @Param("place") String place);
 
}
