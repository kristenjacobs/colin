package jcolin.consoles;

import java.util.Scanner;
import java.util.Vector;

import jcolin.commands.Command;

public class BasicConsole extends Console {

	public BasicConsole(){
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

	@Override
	public String getNextLine(Vector<Command> commandHistory) {
    	Scanner in = new Scanner(System.in);
        return in.nextLine();
	}

	public void clearScreen() {
	}

	public boolean hasEscaped() {
		return false;
	}
	
	private void output(String str, boolean forceOutputDisplay) {
		super.addCommandOutput(str);
		if ((forceOutputDisplay || getOutputDisplay()) && 
			!redirectToFile(str)) {
			System.out.printf("%s", str);
		}		
	}
}
