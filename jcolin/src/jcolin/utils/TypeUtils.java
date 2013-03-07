package jcolin.utils;

import java.util.Map;

import jcolin.consoles.IConsole;

public class TypeUtils {

    /** 
     * Finds the relevant argument from the args map, and does a String type conversion.
     */
    public static String getString(Map<String, String> args, String argName) {
        if (args.containsKey(argName)) {
            return args.get(argName);
        }
        throw new IllegalArgumentException();
    }

    /** 
     * Finds the relevant argument from the args map, and does a int type conversion.
     */
    public static int getInteger(Map<String, String> args, String argName) {
        if (args.containsKey(argName)) {
            try {
                return Integer.parseInt(args.get(argName));             
            } catch (NumberFormatException e) {}
        }
        throw new IllegalArgumentException();
    }   

    /** 
     * Finds the relevant argument from the args map, and does a double type conversion.
     */
    public static double getDouble(Map<String, String> args, String argName) {
        if (args.containsKey(argName)) {
            try {
                return Double.parseDouble(args.get(argName));               
            } catch (NumberFormatException e) {}
        }
        throw new IllegalArgumentException();
    }
    
    /** 
     * Finds the relevant argument from the args map, and does a boolean type conversion.
     */
    public static boolean getBoolean(Map<String, String> args, String argName) {
        if (args.containsKey(argName)) {
            return Boolean.parseBoolean(args.get(argName));             
        }
        throw new IllegalArgumentException();
    }

    /** 
     * Finds the relevant argument from the args map, and does a String type conversion.
     */
    public static String getFile(Map<String, String> args, String argName) {
        if (args.containsKey(argName)) {
            return args.get(argName);
        }
        throw new IllegalArgumentException();
    }   

    /** 
     * Returns a string via the console.
     */
    public static void returnString(IConsole console, String value) {
        console.display(value);
    }

    /** 
     * Returns a int via the console.
     */
    public static void returnInteger(IConsole console, int value) {
        console.display(Integer.toString(value));
    }   

    /** 
     * Returns a double via the console.
     */
    public static void returnDouble(IConsole console, double value) {
        console.display(Double.toString(value));
    }
    
    /** 
     * Returns a boolean via the console.
     */
    public static void returnBoolean(IConsole console, boolean value) {
        console.display(Boolean.toString(value));
    }
    
    /** 
     * Returns a file via the console.
     */
    public static void returnFile(IConsole console, String value) {
        console.display(value);
    }
}
