package jcolin.utils;

import java.util.Map;

import jcolin.consoles.IConsole;

public class TypeUtils {

	public static String getString(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
			return args.get(argName);
		}
		throw new IllegalArgumentException();
	}

	public static int getInteger(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
			try {
				return Integer.parseInt(args.get(argName));				
			} catch (NumberFormatException e) {}
		}
		throw new IllegalArgumentException();
	}	

	public static double getDouble(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
			try {
				return Double.parseDouble(args.get(argName));				
			} catch (NumberFormatException e) {}
		}
		throw new IllegalArgumentException();
	}
	
	public static boolean getBoolean(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
    		return Boolean.parseBoolean(args.get(argName));				
		}
		throw new IllegalArgumentException();
	}
	
	public static String getFile(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
			return args.get(argName);
		}
		throw new IllegalArgumentException();
	}	

	public static void returnString(IConsole console, String value) {
		console.display(value);
	}

	public static void returnInteger(IConsole console, int value) {
		console.display(Integer.toString(value));
	}	

	public static void returnDouble(IConsole console, double value) {
		console.display(Double.toString(value));
	}
	
	public static void returnBoolean(IConsole console, boolean value) {
		console.display(Boolean.toString(value));
	}
	
	public static void returnFile(IConsole console, String value) {
		console.display(value);
	}
}
