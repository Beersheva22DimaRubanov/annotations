package telran.validation.annotation;

import java.lang.reflect.Field;

import telran.annotation.ValidatorInterface;

public class PatternValidator implements ValidatorInterface {

	@Override
	public String getMessage(Field field, Object obj) {
		String res = "";
		field.setAccessible(true);
		try {
			String value = field.get(obj).toString();
			telran.validation.annotation.Pattern pattern = field.getAnnotation(telran.validation.annotation.Pattern.class);
			String regex = pattern.regex();
			if(!value.matches(regex)) {
				res = pattern.errorMessage();
			}
			
		} catch (Exception e) {
			res = e.getMessage();
		}
		return res;
	}

}
