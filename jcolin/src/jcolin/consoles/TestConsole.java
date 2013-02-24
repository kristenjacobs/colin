package jcolin.consoles;

import java.io.IOException;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;

public class TestConsole extends Console {
	
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

	@Override
	public String getNextLine(Vector<Command> commandHistory) {
		assert(false);
    	return null;
	}

	private void output(String str, boolean forceOutputDisplay) {
		super.addCommandOutput(str);
	}
}
