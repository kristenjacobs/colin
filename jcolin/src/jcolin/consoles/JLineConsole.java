package jcolin.consoles;

import java.io.IOException;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.completors.JColinCompletor;
import jline.ConsoleReader;

public class JLineConsole extends Console {
	
	private ConsoleReader m_reader;
	private String m_prompt;

	JLineConsole() throws IOException {
		m_reader = new ConsoleReader();
		m_reader.setBellEnabled(false);
	}

	public void initialise(CommandFactory commandFactory) throws IOException {
	  	m_reader.addCompletor(new JColinCompletor(commandFactory));
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
		m_prompt = str;
	}

	public String getNextLine(Vector<Command> commandHistory) {
		String line = null;
		while (line == null) {
			try {
    			line = m_reader.readLine(m_prompt);
	    		if (line == null) {
		   			reportError();
		    	    return null;
			    }

		    } catch (IOException e) {
	            line = null;
		    }
		}
		return line;
	}

	private void reportError() {
		System.out.printf("internal error: Problem reading console input (aborting)..\n");
	    System.out.printf("try using -DConsole=Basic\n");
		System.exit(1);
	}
	
	private void output(String str, boolean forceOutputDisplay) {
		super.addCommandOutput(str);		
		if (outputEnabled(forceOutputDisplay) &&
			!redirectToFile(str)) {
			System.out.printf("%s", str);
		}		
	}	
}
