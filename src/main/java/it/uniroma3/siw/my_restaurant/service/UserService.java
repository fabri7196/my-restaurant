package it.uniroma3.siw.my_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id){
        this.userRepository.deleteById(id);
    }
}
