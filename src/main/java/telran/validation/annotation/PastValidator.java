package telran.validation.annotation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.Temporal;

import telran.annotation.ValidatorInterface;

public class PastValidator implements ValidatorInterface {

	@Override
	public String getMessage(Field field, Object obj) {
		String res = "";
		field.setAccessible(true);
		try {

			Object value = field.get(obj);
			if (value instanceof Temporal) {
				Temporal temporal = (Temporal) value;
				LocalDate temporalDate = LocalDate.from(temporal);
				Past past = field.getAnnotation(telran.validation.annotation.Past.class);
				LocalDate now = LocalDate.now();
				if (!temporalDate.isBefore(now)) {
					res = past.errorMessage();
				}
			} else {
				res = "Input should be date";
			}

		} catch (Exception e) {
			res = e.getMessage();
		}
		return res;
	}

}
