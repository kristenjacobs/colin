package jcolin.commands;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.Shell;
import jcolin.consoles.Console;

public abstract class Command {
	public static final String REDIRECT_OVERWITE = ">";
	public static final String REDIRECT_APPEND   = ">>";

    public enum ArgType {
    	FILE,
    	HELP,
    	HELPCOMMAND,
    	IGNORE,
    }
    public enum RedirectMode {
    	OVERWRITE,
    	APPEND,
    }

    private String m_fileRedirect;
    private RedirectMode m_fileRedirectMode;
	private String[] m_commandNames;

    public abstract int numArgs();
    public abstract String commandLine();
    public abstract String description(boolean detailed);
    public abstract Command clone(String[] args, int index);
    public abstract void execute(Shell shell, Object model, Console console);
    
    public Command(String[] commandNames) {
    	m_commandNames = commandNames;
    }
    
    public String name() {
    	return m_commandNames[0];
    }
    
    public String[] names() {
    	return m_commandNames;
    }
   
    public boolean matches(String arg) {
    	for (String command : m_commandNames) {
    		if (command.equals(arg)) {
    			return true;
    		}
    	}
    	return false;
    }

	public String toString(){
		return commandLine();
	}

	public Collection<Command> getSubCommands() {
		return new ArrayList<Command>();
	}

	public ArgType[] getArgTypes() {
		return new ArgType[] {};
	}

    protected boolean areArgsDone(String[] args, int i) {
		if ((i >= args.length) ||
			(args[i].startsWith("-") && (args[i].length() > 1)) ||
			args[i].startsWith("\n") ||
			args[i].equals(REDIRECT_OVERWITE) ||
			args[i].equals(REDIRECT_APPEND)) {
			return true;
		}
		return false;
	}

    public void setFileRedirect(String fileRedirect) {
    	m_fileRedirect = fileRedirect;
    }

    public String getFileRedirect() {
    	return m_fileRedirect;
    }

    public void setFileRedirectMode(RedirectMode fileRedirectMode) {
    	m_fileRedirectMode = fileRedirectMode;
    }

    public RedirectMode getFileRedirectMode() {
    	return m_fileRedirectMode;
    }

    public String getFullCommandLine() {
		if (m_fileRedirect != null) {
			switch (m_fileRedirectMode) {
			case OVERWRITE:
				return commandLine() + " " + REDIRECT_OVERWITE + " " + m_fileRedirect;

			case APPEND:
				return commandLine() + " " + REDIRECT_APPEND + " " + m_fileRedirect;

			default:
				return commandLine();
			}
		}
		return commandLine();
    }
}

