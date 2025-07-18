package it.uniroma3.siw.my_restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.credentials != :credentials")
    public List<User> getAllUsersExceptCurrent(@Param("credentials")Credentials credentials);

    public User findByEmail(String email);

}
