package jcolin.commands;

import jcolin.consoles.Console;
import jcolin.shell.Shell;

public abstract class Container extends Command {
    private Command m_option;

    public Container(String[] commandNames) {
        super(commandNames);
        m_option = null;
    }

    public Container(String[] commandNames, Command option) {
        super(commandNames);
        m_option = option;
    }

    abstract protected String getDescription();
    abstract protected Command clone(Command opt);

    public int numArgs() {
        return m_option.numArgs() + 1;
    }

    public String commandLine() {
        return String.format("%s %s", name(), m_option.commandLine());
    }

    public String description(boolean detailed) {
        String descString = String.format("%s <option> <arg0> <arg1>...: %s", name(), getDescription());
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        if (detailed) {
            for (Command option : getSubCommands()) {
                sb.append("      option: ");
                sb.append(option.description(detailed));
                sb.append( "\n");
            }               
        }
        String optionsString= sb.toString();
        descString += optionsString.substring(0, optionsString.length()-1);
        return descString;
    }

    public Command clone(String[] args, int index) {
        if ((index + 1) >= args.length)
            return null;

        String arg = args[index + 1];
        for (Command option : getSubCommands()) {
            if (option.matches(arg)) {
                Command opt = option.clone(args, index + 1);
                if (opt == null)
                    return null;

                return clone(opt);
            }
        }
        return null;
    }

    public void execute(Shell shell, Object model, Console console) {
        m_option.execute(shell, model, console);
    }    
}
