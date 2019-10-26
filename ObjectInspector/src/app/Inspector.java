package app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

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
    	Constructor<?>[] constructors = c.getDeclaredConstructors();
    	String indent = getTabs(depth);
    	
    	for (int i = 0; i < constructors.length; i+=1) {
    		System.out.println(indent + "Constructor " + Integer.toString(i) + ":");
    		constructors[i].setAccessible(true);
    		System.out.println(indent + " Name: " + constructors[i].getName());
    		inspectParameters(constructors[i], depth);
    		System.out.println(indent + " Modifier: " + Modifier.toString(constructors[i].getModifiers()));
    	}
    	
    }
    
    private void inspectMethods(Class<?> c, int depth) {
    	Method[] methods = c.getDeclaredMethods();
    	String indent = getTabs(depth);
    	
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
    	Field[] fields = c.getDeclaredFields();
    	Object contained_obj;
    	Class<?> contained_obj_class;
    	
    	for (int i = 0; i < fields.length; i+=1) {
    		System.out.println(indent + "Field " + Integer.toString(i) + ":");
    		fields[i].setAccessible(true);
    		System.out.println(indent + " Name: " + fields[i].getName());
    		System.out.println(indent + " Type: " + fields[i].getType());
    		System.out.println(indent + " Modifier: " + Modifier.toString(fields[i].getModifiers()));
    		try {
    			contained_obj = fields[i].get(obj);
    			if (contained_obj == null) {
    				System.out.println(indent + " Value: null");
    			} else {
    				contained_obj_class = contained_obj.getClass();
        			if (isWrapperType(contained_obj_class)) {
        				System.out.println(indent + " Value: " + contained_obj);
        			} else {
        				if (recursive) {
            				System.out.println(indent + " Value: ");
            				inspectClass(contained_obj_class, contained_obj, true, depth + 1);
            			} else {
            				System.out.println(indent + " Value: " + contained_obj_class.getCanonicalName()
            						+ "@" + System.identityHashCode(contained_obj));
            			}
        			}
    			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes()
    {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }
    
    private String getTabs(int num_tabs) {
    	String tabs = "";
    	for (int i = 0; i < num_tabs; i++) {
    		tabs += "\t";
    	}
    	return tabs;
    }

}
