package it.uniroma3.siw.my_restaurant.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.repository.CredentialsRepository;

@Service
public class CredentialsService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    CredentialsRepository credentialsRepository;

    CredentialsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Credentials getCredentials(Long id) {
        return this.credentialsRepository.findById(id).get();
    }

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> optionalCredentials = credentialsRepository.findByUsername(username);
            if (optionalCredentials.isPresent()) {
                Credentials credentials = optionalCredentials.get();
                return credentials;
            } 
        return null;
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public void deleteUser(String username){
        Credentials credentials = this.credentialsRepository.findByUsername(username).get();
        this.credentialsRepository.delete(credentials);
    }
}
