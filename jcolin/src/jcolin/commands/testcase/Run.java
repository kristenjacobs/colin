package jcolin.commands.testcase;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class Run extends Command {	
	private static final String[] COMMAND_NAMES = { "run" };

	private String m_testcase;
	
	public Run() {
		super(COMMAND_NAMES);
		m_testcase = "";
	}

	public Run(String testcase) {
		super(COMMAND_NAMES);
		m_testcase = testcase;
	}

    public int numArgs() {
    	return 1;    		
    }

    public String commandLine() {
   		return name() + " " + m_testcase;
    }

    public String description(boolean detailed) {
        return name() + " <testcase name>: Runs the given testcase";
    }

    public Command clone(String[] args, int index) {
		if ((index + numArgs()) >= args.length)
			return null;

    	String testcase = args[index + 1];
    	return new Run(testcase);
    }

    public void execute(Shell shell, Object model, Console console) {
    }
}
