package app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class<?> c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class<?> c, Object obj, boolean recursive, int depth) {
    	String indent = getTabs(depth);
    	
    	System.out.println(indent + "Declaring Class Name: " + c.getCanonicalName());
    	inspectSuperClass(c, obj, depth);
    	inspectInterfaces(c, obj, depth);
    	inspectConstructors(c, depth);
    	inspectMethods(c, depth);
    	inspectFields(c, obj, recursive, depth);
    }
    
    private void inspectSuperClass(Class<?> c, Object obj, int depth) {
    	Class<?> super_class;
    	String indent = getTabs(depth);
    	
    	super_class = c.getSuperclass();
    	System.out.println(indent + "Super-class: ");
    	if (super_class != Object.class && super_class != null) {
    		inspectClass(super_class, obj, true, depth + 1);
    	} else {
    		System.out.println(indent + " None");
    	}
    }
    
    private void inspectInterfaces(Class<?> c, Object obj, int depth) {
    	Class<?>[] interfaces;
    	String indent = getTabs(depth);
    	
    	interfaces = c.getInterfaces();
    	System.out.println(indent + "Interfaces: ");
    	for (int i = 0; i < interfaces.length; i+=1) {
    		inspectClass(interfaces[i], obj, true, depth + 1);
    	}
    	if (interfaces.length == 0) {
    		System.out.println(indent + " None");
    	}
    }
    
    private void inspectConstructors(Class<?> c, int depth) {
    	Constructor<?>[] constructors;
    	String indent = getTabs(depth);
    	
    	constructors = c.getDeclaredConstructors();
    	for (int i = 0; i < constructors.length; i+=1) {
    		System.out.println(indent + "Constructor " + Integer.toString(i) + ":");
    		constructors[i].setAccessible(true);
    		System.out.println(indent + " Name: " + constructors[i].getName());
    		inspectParameters(constructors[i], depth);
    		System.out.println(indent + " Modifier: " + Modifier.toString(constructors[i].getModifiers()));
    	}
    	
    }
    
    private void inspectMethods(Class<?> c, int depth) {
    	Method[] methods;
    	String indent = getTabs(depth);
    	
    	methods = c.getDeclaredMethods();
    	for (int i = 0; i < methods.length; i+=1) {
    		System.out.println(indent + "Method " + Integer.toString(i) + ":");
    		methods[i].setAccessible(true);
    		System.out.println(indent + " Name: " + methods[i].getName());
    		
    		inspectParameters(methods[i], depth);
    		inspectExceptions(methods[i], depth);
    		
    		System.out.println(indent + " Return Type: " + methods[i].getReturnType());
    		System.out.println(indent + " Modifier: " + Modifier.toString(methods[i].getModifiers()));
    	}
    }
    
    private void inspectParameters(Executable executable, int depth) {
    	String indent = getTabs(depth);
    	Parameter[] parameters = executable.getParameters();
    	
    	if (parameters.length > 0) {
			System.out.print(indent + " Parameter Types: " + parameters[0].getType());
			for (int l = 1; l < parameters.length; l+=1) {
    			System.out.print(", " + parameters[l].getType());
    		}
			System.out.println();
		} else {
			System.out.println(indent + " Parameter Types: None");
		}
    }
    
    private void inspectExceptions(Executable executable, int depth) {
    	String indent = getTabs(depth);
    	Class<?>[] exceptions = executable.getExceptionTypes();
    	
		if (exceptions.length > 0) {
			System.out.print(indent + " Throws Exceptions: " + exceptions[0].getCanonicalName());
			for (int l = 1; l < exceptions.length; l+=1) {
    			System.out.print(", " + exceptions[l].getCanonicalName());
    		}
			System.out.println();
		} else {
			System.out.println(indent + " Throws Exceptions: None");
		}
    }
    
    private void inspectFields(Class<?> c, Object obj, boolean recursive, int depth) {
    	String indent = getTabs(depth);
    	Field[] fields;
    	
    	fields = c.getDeclaredFields();
    	for (int i = 0; i < fields.length; i+=1) {
    		System.out.println(indent + "Field " + Integer.toString(i) + ":");
    		fields[i].setAccessible(true);
    		System.out.println(indent + " Name: " + fields[i].getName());
    		System.out.println(indent + " Type: " + fields[i].getType());
    		System.out.println(indent + " Modifier: " + Modifier.toString(fields[i].getModifiers()));
    		try {
				System.out.println(indent + " Value: " + fields[i].get(obj));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private String getTabs(int num_tabs) {
    	String tabs = "";
    	for (int i = 0; i < num_tabs; i++) {
    		tabs += "\t";
    	}
    	return tabs;
    }

}
