package jcolin.commands.echo;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

public class Echo extends Command {
    private static final String[] COMMAND_NAMES = { "echo" };

    private String m_string;

    public Echo() {
        super(COMMAND_NAMES);
    }

    public Echo(String string) {
        super(COMMAND_NAMES);
        m_string = string;
    }

    public int numArgs() {
        return 1;
    }

    public String commandLine() {
        return name() + " \"" + m_string + "\"";
    }

    public String description(boolean detailed) {
        return name() + " \"text\": Prints the text to the console";
    }

    public Command clone(String[] args, int index) {
        if ((index + numArgs()) >= args.length)
            return null;

        return new Echo(args[index + 1]);
    }

    public void execute(Shell shell, Object model, Console console) {
        console.display(m_string + "\n");
    }
}