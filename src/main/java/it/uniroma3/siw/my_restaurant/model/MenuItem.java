package it.uniroma3.siw.my_restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "menuItem_seq")
public class MenuItem {

	public static final String APPRETIZER = "Antipasto";
	public static final String FIRST_COURSE  = "Primo";
	public static final String SECOND_COURSE = "Secondo";
	public static final String SIDE = "Contorno";
	public static final String SWEET = "Dolce";

	public static final String LUNCH = "Pranzo";
	public static final String DINNER = "Cena";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "menuItem_seq")
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;

	private Double price;

	//Primo, secondo, contorno ecc..
	@NotBlank
	private String category;

	//Pranzo o cena
	private String meal;

	@ManyToOne
	private User user;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getMeal() {
		return meal;
	}
	public void setMeal(String meal) {
		this.meal = meal;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}