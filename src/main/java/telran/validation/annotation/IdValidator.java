package telran.validation.annotation;

import java.lang.reflect.Field;

import telran.annotation.ValidatorInterface;

public class IdValidator implements ValidatorInterface {
	public static final String NO_ID_MESSAGE = "A class has to have annotatef by Id";
	public static final String SEVERAL_IDS_MESSAGE = "Field annotated by Id must be only one";
	private static  int count = 0;
	
	
//	public static Object getId(Object obj) throws Exception {
//		Class<?> clazz = obj.getClass();
//		Field[] fields = clazz.getDeclaredFields();
//		Field field = getFieldId(fields);
//		return field.get(obj);
//
//	} 
//
//	private static Field getFieldId(Field[] fields) {
//		Field res = null;
//		for (Field field : fields) {
//			field.setAccessible(true);
//			if (field.isAnnotationPresent(Id.class)) {
//				if (res != null) {
//					throw new IllegalArgumentException(SEVERAL_IDS_MESSAGE);
//				}
//				res = field;
//			}
//		}
//		if (res == null) {
//			throw new IllegalArgumentException(NO_ID_MESSAGE);
//		}
//		return res;
//	}


	@Override
	public String getMessage(Field field, Object obj) {
		String res = "";
		field.setAccessible(true);
		try {
			if (field.isAnnotationPresent(Id.class)) {
				if (count > 0) {
					res = SEVERAL_IDS_MESSAGE;
				}
				count++;
				res = "Ok";
			}
//		if (count == 0) {
//			throw new IllegalArgumentException(NO_ID_MESSAGE);
//		}
			
		} catch (Exception e) {
			res = e.getMessage();
		}
		return res;
	}

}
