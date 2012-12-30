package jcolin.consoles;

import java.io.IOException;

import jcolin.commands.CommandFactory;

public class ConsoleFactory {
	public static Console buildConsole(CommandFactory commandFactory) {
		
	    String consoleProperty = System.getProperty("Console");
	    if ((consoleProperty != null) && (consoleProperty.equals("Basic"))) {
	    	return new BasicConsole();
	    }

    	try {
			return new JLineConsole(commandFactory);
			
		} catch (IOException e) {
			return new BasicConsole();
		}
	}
}
