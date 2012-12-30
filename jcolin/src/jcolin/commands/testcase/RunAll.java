package jcolin.commands.testcase;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class RunAll extends Command {	
	private static final String[] COMMAND_NAMES = { "runall" };

	public RunAll() {
		super(COMMAND_NAMES);
	}

    public int numArgs() {
    	return 0;    		
    }

    public String commandLine() {
   		return name();
    }

    public String description(boolean detailed) {
        return name() + ": Runs all the testcases";
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
