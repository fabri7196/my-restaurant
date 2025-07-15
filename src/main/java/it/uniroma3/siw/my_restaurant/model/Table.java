package it.uniroma3.siw.my_restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "table_seq")
public class Table {

	@Id
	@GeneratedValue(generator = "table_seq", strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Min(0)
	@Max(10)
	private int numberTable;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberTable() {
		return this.numberTable;
	}
	public void setNumberTable(int numberTable) {
		this.numberTable = numberTable;
	}
	
}