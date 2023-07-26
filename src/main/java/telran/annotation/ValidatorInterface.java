package telran.annotation;

import java.lang.reflect.Field;

public interface ValidatorInterface {
	String getMessage(Field field, Object obj);
}
