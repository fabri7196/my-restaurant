package it.uniroma3.siw.my_restaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.model.User;
import it.uniroma3.siw.my_restaurant.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User getUser(Long id) {
        return this.userRepository.findById(id).get();
    }

    @Transactional
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsersExceptCurrent(Credentials credentials) {
        List<User> result = new ArrayList<>();
        result = this.userRepository.getAllUsersExceptCurrent(credentials);
        
        if(result.isEmpty()) {
            return List.of();
        }

        return result;
    }

    @Transactional
    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
}
