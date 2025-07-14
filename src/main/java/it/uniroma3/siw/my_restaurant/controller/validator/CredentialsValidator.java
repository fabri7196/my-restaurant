package it.uniroma3.siw.my_restaurant.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.my_restaurant.model.Credentials;
import it.uniroma3.siw.my_restaurant.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {

	@Autowired
	private CredentialsService credentialsService;

    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials)target;
		String username = credentials.getUsername();

		if (username.length() < MIN_USERNAME_LENGTH)
            errors.rejectValue("username", "size.usernameMin");

        if (username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "size.usernameMax");
		
		if(this.credentialsService.getCredentials(username) != null) {
			errors.rejectValue("username","username.duplicato");
		}
			
	}

}
