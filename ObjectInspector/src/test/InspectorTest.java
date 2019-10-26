package test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.Inspector;
import driver.ClassA;

class InspectorTest {
	
	private PrintStream sysOut;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ClassA testA = new ClassA();
    Inspector inspector = new Inspector();
    Method inspectFields;
    Method inspectMethods;
    Method inspectConstructors;
    Method inspectInterfaces;
    
	@BeforeEach
	void setUp() throws Exception {
		inspectFields = inspector.getClass().getDeclaredMethod("inspectFields", new Class[] {Class.class, Object.class, boolean.class, int.class});
		inspectMethods = inspector.getClass().getDeclaredMethod("inspectMethods", new Class[] {Class.class, int.class});
		inspectFields.setAccessible(true);
		inspectMethods.setAccessible(true);
		inspectConstructors = inspector.getClass().getDeclaredMethod("inspectConstructors", new Class[] {Class.class, int.class});
		inspectConstructors.setAccessible(true);
		inspectInterfaces = inspector.getClass().getDeclaredMethod("inspectInterfaces", new Class[] {Class.class, Object.class, int.class});
		inspectInterfaces.setAccessible(true);
		sysOut = System.out;
        System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	void tearDown() throws Exception {
		System.setOut(sysOut);
	}

	@Test
	void testPrimitiveFields() {
		String expected = "Field 0 of driver.ClassA with depth: 0\r\n" + 
				" Name: val\r\n" + 
				" Type: int\r\n" + 
				" Modifier: private\r\n" + 
				" Value: 3\r\n" + 
				"Field 1 of driver.ClassA with depth: 0\r\n" + 
				" Name: val2\r\n" + 
				" Type: double\r\n" + 
				" Modifier: private\r\n" + 
				" Value: 0.2\r\n" + 
				"Field 2 of driver.ClassA with depth: 0\r\n" + 
				" Name: val3\r\n" + 
				" Type: boolean\r\n" + 
				" Modifier: private\r\n" + 
				" Value: true\r\n" + 
				"";
		try {
			inspectFields.invoke(inspector, testA.getClass(), testA, true, 0);
			String actual = outContent.toString();
			assertEquals(expected, actual);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	void testMethods() {
		String expected = "Method 0 of driver.ClassA with depth: 0\r\n" + 
				" Name: run\r\n" + 
				" Parameter Types: None\r\n" + 
				" Throws Exceptions: None\r\n" + 
				" Return Type: void\r\n" + 
				" Modifier: public\r\n" + 
				"Method 1 of driver.ClassA with depth: 0\r\n" + 
				" Name: toString\r\n" + 
				" Parameter Types: None\r\n" + 
				" Throws Exceptions: None\r\n" + 
				" Return Type: class java.lang.String\r\n" + 
				" Modifier: public\r\n" + 
				"Method 2 of driver.ClassA with depth: 0\r\n" + 
				" Name: setVal\r\n" + 
				" Parameter Types: int\r\n" + 
				" Throws Exceptions: java.lang.Exception\r\n" + 
				" Return Type: void\r\n" + 
				" Modifier: public\r\n" + 
				"Method 3 of driver.ClassA with depth: 0\r\n" + 
				" Name: getVal\r\n" + 
				" Parameter Types: None\r\n" + 
				" Throws Exceptions: None\r\n" + 
				" Return Type: int\r\n" + 
				" Modifier: public\r\n" + 
				"Method 4 of driver.ClassA with depth: 0\r\n" + 
				" Name: printSomething\r\n" + 
				" Parameter Types: None\r\n" + 
				" Throws Exceptions: None\r\n" + 
				" Return Type: void\r\n" + 
				" Modifier: private\r\n" + 
				"";
		try {
			inspectMethods.invoke(inspector, testA.getClass(), 0);
			String actual = outContent.toString();
			assertEquals(expected, actual);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	void testConstructors() {
		String expected = "Constructor 0 of driver.ClassA with depth: 0\r\n" + 
				" Name: driver.ClassA\r\n" + 
				" Parameter Types: None\r\n" + 
				" Modifier: public\r\n" + 
				"Constructor 1 of driver.ClassA with depth: 0\r\n" + 
				" Name: driver.ClassA\r\n" + 
				" Parameter Types: int\r\n" + 
				" Modifier: public\r\n" + 
				"";
		try {
			inspectConstructors.invoke(inspector, testA.getClass(), 0);
			String actual = outContent.toString();
			assertEquals(expected, actual);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	void testInterfaces() {
		String expected = "Interfaces: \r\n" + 
				"Entering new class with depth: 1\r\n" + 
				"	Declaring Class Name: public abstract interface java.io.Serializable\r\n" + 
				"	Super-class: \r\n" + 
				"	 None\r\n" + 
				"	Interfaces: \r\n" + 
				"	 None\r\n" + 
				"Entering new class with depth: 1\r\n" + 
				"	Declaring Class Name: public abstract interface java.lang.Runnable\r\n" + 
				"	Super-class: \r\n" + 
				"	 None\r\n" + 
				"	Interfaces: \r\n" + 
				"	 None\r\n" + 
				"	Method 0 of java.lang.Runnable with depth: 1\r\n" + 
				"	 Name: run\r\n" + 
				"	 Parameter Types: None\r\n" + 
				"	 Throws Exceptions: None\r\n" + 
				"	 Return Type: void\r\n" + 
				"	 Modifier: public abstract\r\n" + 
				"";
		try {
			inspectInterfaces.invoke(inspector, testA.getClass(), testA, 0);
			String actual = outContent.toString();
			assertEquals(expected, actual);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
