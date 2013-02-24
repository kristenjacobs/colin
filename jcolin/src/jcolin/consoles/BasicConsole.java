package jcolin.consoles;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;

public class BasicConsole extends Console {

	public void initialise(CommandFactory commandFactory) throws IOException {		
	}
	
	public void display(String str) {
		output(str, false);
	}

	public void displayError(String str) {
		output("error: " + str, true);
	}

	public void displayWarning(String str) {
		output("warning: " + str, true);		
	}
	
	public void displayInfo(String str) {
		output("info: " + str, true);
	}
	
	public void displayPrompt(String str) {
		System.out.printf("%s", str);
	}

	@SuppressWarnings("resource")
	@Override
	public String getNextLine(Vector<Command> commandHistory) {
    	return new Scanner(System.in).nextLine();
	}

	private void output(String str, boolean forceOutputDisplay) {
		super.addCommandOutput(str);
		if (outputEnabled(forceOutputDisplay) &&
			!redirectToFile(str)) {
			System.out.printf("%s", str);
		}		
	}
}
