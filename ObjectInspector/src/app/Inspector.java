package app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class<?> c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class<?> c, Object obj, boolean recursive, int depth) {
    	Class<?> super_class;
    	Class<?>[] interfaces;
    	Constructor<?>[] constructors;
    	Parameter[] param;
    	Method[] methods;
    	String indent = getTabs(depth);
    	Class<?>[] exceptions;
    	
    	System.out.println(indent + "Declaring Class Name: " + c.getCanonicalName());
    	
    	super_class = c.getSuperclass();
    	System.out.println(indent + "Super-class: ");
    	if (super_class != Object.class && super_class != null) {
    		inspectClass(super_class, obj, recursive, depth + 1);
    	} else {
    		System.out.println(indent + " None");
    	}
    	
    	interfaces = c.getInterfaces();
    	System.out.println(indent + "Interfaces: ");
    	for (int i = 0; i < interfaces.length; i+=1) {
    		inspectClass(interfaces[i], obj, recursive, depth + 1);
    	}
    	if (interfaces.length == 0) {
    		System.out.println(indent + " None");
    	}
    	
    	constructors = c.getDeclaredConstructors();
    	for (int i = 0; i < constructors.length; i+=1) {
    		System.out.println(indent + "Constructor " + Integer.toString(i));
    		constructors[i].setAccessible(true);
    		System.out.println(indent + " Name: " + constructors[i].getName());
    		param = constructors[i].getParameters();
    		if (param.length > 0) {
    			System.out.print(indent + " Parameter Types: " + param[0].getType());
    			for (int l = 1; l < param.length; l+=1) {
        			System.out.print(", " + param[l].getType());
        		}	
    		} else {
    			System.out.print(indent + " Parameter Types: None");
    		}
    		System.out.println("\n" + indent + " Modifier: " + Modifier.toString(constructors[i].getModifiers()));
    	}
    	
    	methods = c.getDeclaredMethods();
    	for (int i = 0; i < methods.length; i+=1) {
    		System.out.println(indent + "Method " + Integer.toString(i));
    		methods[i].setAccessible(true);
    		System.out.println(indent + " Name: " + methods[i].getName());
    		exceptions = methods[i].getExceptionTypes();
    		if (exceptions.length > 0) {
    			System.out.print(indent + " Throws Exceptions: " + exceptions[0].getCanonicalName());
    			for (int l = 1; l < exceptions.length; l+=1) {
        			System.out.print(", " + exceptions[l].getCanonicalName());
        		}
    			System.out.println();
    		} else {
    			System.out.println(indent + " Exceptions: None");
    		}
    		param = methods[i].getParameters();
    		if (param.length > 0) {
    			System.out.print(indent + " Parameter Types: " + param[0].getType());
    			for (int l = 1; l < param.length; l+=1) {
        			System.out.print(", " + param[l].getType());
        		}	
    		} else {
    			System.out.print(indent + " Parameter Types: None");
    		}
    		System.out.println("\n" + indent + " Modifier: " + Modifier.toString(methods[i].getModifiers()));
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
