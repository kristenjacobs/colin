package jcolin.commands.exit;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class Exit extends Command {
    private static final String[] COMMAND_NAMES = { "exit", "quit" };

    public Exit() {
        super(COMMAND_NAMES);
    }

    public int numArgs() {
        return 0;
    }

    public String commandLine() {
        return name();
    }

    public String description(boolean detailed) {
        return name() + ": Quits the application";
    }

    public Command clone(String[] args, int index) {
        return new Exit();
    }

    public void execute(Shell shell, Object model, Console console) {
        shell.exit(1);
    }
}
