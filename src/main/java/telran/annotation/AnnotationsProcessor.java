package telran.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import telran.validation.annotation.Id;
import telran.validation.annotation.Validation;

public class AnnotationsProcessor {
	public static final String NO_ID_MESSAGE = "A class has to have annotatef by Id";
	public static final String SEVERAL_IDS_MESSAGE = "Field annotated by Id must be only one";
	
	public static Object getId(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Field field = getFieldId(fields);
		return field.get(obj);

	} 

	private static Field getFieldId(Field[] fields) {
		Field res = null;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				if (res != null) {
					throw new IllegalArgumentException(SEVERAL_IDS_MESSAGE);
				}
				res = field;
			}
		}
		if (res == null) {
			throw new IllegalArgumentException(NO_ID_MESSAGE);
		}
		return res;
	}
	
	public static List<String> validate(Object obj){
		List<String> errorMessages = new ArrayList<>();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field: fields) {
			validateField(field, errorMessages, obj);
		}
		return errorMessages;
		   	}
	
	private static void validateField(Field field, List<String> errorMessages, Object obj) {
		Annotation [] annotations = field.getAnnotations();
		List<Annotation> validateAnotations = Arrays.stream(annotations)
			.filter(a -> a.annotationType().isAnnotationPresent(Validation.class)).toList();
		validateAnotations.forEach(a -> validateAnotation(a, field, errorMessages, obj));
	}

	private static void validateAnotation(Annotation a, Field field, List<String> errorMessages, Object obj) {
		String annotationName = a.getClass().getName();
		String validatorName = annotationName + "Validator";
		try {
			Class<?> validatorClass = Class.forName(validatorName);
			Constructor<?> construcror = validatorClass.getDeclaredConstructor();
			Object annotationValidator = (ValidatorInterface) construcror.newInstance();
			String message = ((ValidatorInterface) annotationValidator).getMessage(field, obj);
			if(!message.isEmpty()) {
				errorMessages.add(message);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
