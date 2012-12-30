package jcolin.utils;

import java.util.Map;

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

	public static float getFloat(Map<String, String> args, String argName) {
		if (args.containsKey(argName)) {
			try {
				return Float.parseFloat(args.get(argName));				
			} catch (NumberFormatException e) {}
		}
		throw new IllegalArgumentException();
	}
}
