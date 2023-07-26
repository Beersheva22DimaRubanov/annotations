package telran.annotation;

import static org.junit.jupiter.api.Assertions.*;
import static telran.annotation.AnnotationsProcessor.*;
import org.junit.jupiter.api.Test;

import telran.validation.annotation.Id;

class AnnotationProcessorTest {

	@Test
	void getIdTest() throws Exception {
		String email = "stam@gmail.com";
		
		assertEquals(email, getId(new Xright(email, email)));
		assertThrowsExactly(IllegalArgumentException.class, ()-> getId(new XnoId(email, email)), NO_ID_MESSAGE);
		assertThrowsExactly(IllegalArgumentException.class, ()-> getId(new XtwoId(email, email)), SEVERAL_IDS_MESSAGE);
	}

}

class Xright {
	public Xright(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	@Id
	String email;
	String name;
}

class XnoId{
	public XnoId(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	
	String email;
	String name;
	
}

class XtwoId{
	public XtwoId(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	@Id
	String email;
	@Id
	String name;
	
}
