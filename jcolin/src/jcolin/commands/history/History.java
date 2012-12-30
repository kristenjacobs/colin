package jcolin.commands.history;

import jcolin.Shell;
import jcolin.commands.Command;
import jcolin.consoles.Console;

public class History extends Command {
	private static final String[] COMMAND_NAMES = { "history" };

	public History() {
		super(COMMAND_NAMES);
	}

    public int numArgs() {
    	return 0;
    }

    public String commandLine() {
    	return name();
    }

    public String description(boolean detailed) {
        return name() + ": Displays the command history";
    }

    public Command clone(String[] args, int index) {
        return new History();
    }

    public void execute(Shell shell, Object model, Console console) {
    	int index = 1;
    	for (Command command : shell.getCommandHistory()) {
    		console.display(String.format("%d: %s", index, command.getFullCommandLine()));
    		console.display("\n");
    		++index;
    	}
    }
}
