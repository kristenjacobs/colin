package jcolin.completors;

import java.util.List;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jline.Completor;

public class HelpCompletor implements Completor {
    
    private CommandFactory m_commandFactory;
    
    public HelpCompletor(CommandFactory commandFactory) {
        m_commandFactory = commandFactory;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int complete(String buffer, int cursor, List candidates) {
        String start = (buffer == null) ? "" : buffer;

        // Checks for matching top level commands..
        for (Command command : m_commandFactory.getCommands()) {
            if (command.name().startsWith(start)) {
                candidates.add(command.name());
            }
        }
        return 0;
    }
}
