package it.uniroma3.siw.my_restaurant.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.my_restaurant.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator {

	final Integer MAX_NAME_SURNAME_LENGTH = 60;
	final Integer MIN_NAME_SURNAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		String name = user.getName();
		String surname = user.getSurname();

		if (name != null) {
			if (name.trim().length() < MIN_NAME_SURNAME_LENGTH || name.trim().length() > MAX_NAME_SURNAME_LENGTH)
			errors.rejectValue("name", "size.user.name");
		}

		if (surname != null) {
			if (surname.trim().length() < MIN_NAME_SURNAME_LENGTH || surname.trim().length() > MAX_NAME_SURNAME_LENGTH)
			errors.rejectValue("surname", "size.user.cognome");
		}
		
	}

}
