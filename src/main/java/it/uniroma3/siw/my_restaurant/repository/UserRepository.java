package it.uniroma3.siw.my_restaurant.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.my_restaurant.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
