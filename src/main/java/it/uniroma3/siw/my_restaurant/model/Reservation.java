package it.uniroma3.siw.my_restaurant.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "res_seq")
public class Reservation {
    
	public static final String ESTERNO_POSTO = "ESTERNO";
	public static final String INTERNO_POSTO = "INTERNO";

	@Id
	@GeneratedValue(generator = "res_seq", strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Min(value = 1, message ="*Puoi prenotare al minimo 1 persona")
	@Max(value = 30, message = "*Puoi prenotare al massimo 30 persone")
	private Integer numberOfPerson;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dateOfReservation;
	
	@DateTimeFormat(pattern = "HH:mm")
	@NotNull
	private LocalTime time;

	@NotBlank
	private String place;

	@ManyToOne
	private User user;

	@NotBlank
	private String phoneNumber;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberOfPerson() {
		return this.numberOfPerson;
	}
	public void setNumberOfPerson(Integer numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}

	public LocalDate getDateOfReservation() {
		return this.dateOfReservation;
	}
	public void setDateOfReservation(LocalDate dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getPlace() {
		return this.place;
	}
	public void setPlace(String place) {
		this.place = place;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalTime getTime() {
		return this.time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}

}
