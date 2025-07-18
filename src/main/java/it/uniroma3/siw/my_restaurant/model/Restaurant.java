package it.uniroma3.siw.my_restaurant.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "rest_seq")
public class Restaurant {
		
	@Id
	@GeneratedValue(generator = "rest_seq", strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id")
	private List<Reservation> reservations;
	
	@OneToMany
	private List<User> managers;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Menu menu;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}
	public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
	}

	public List<User> getManagers() {
		return this.managers;
	}
	public void setManagers(List<User> managers) {
        this.managers = managers;
	}

	public Menu getMenu() {
		return this.menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
