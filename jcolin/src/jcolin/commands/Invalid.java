package jcolin.commands;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class Invalid extends Command {
	private static final String[] COMMAND_NAMES = { "" };

	private String m_invalidCommand;
	private String m_errorMessage;

	public Invalid(String invalidCommand, String errorMessage) {
		super(COMMAND_NAMES);
		m_invalidCommand = invalidCommand;
		m_errorMessage = errorMessage;
	}

    public int numArgs() {
    	return 1;
    }

    public String commandLine() {
    	return m_invalidCommand;
    }

    public String description(boolean detailed) {
        return "";
    }

    public Command clone(String[] args, int index) {
    	assert false;
        return null;
    }

    public void execute(Shell shell, Object model, Console console) {
    	System.err.println(m_errorMessage);
    }
}
