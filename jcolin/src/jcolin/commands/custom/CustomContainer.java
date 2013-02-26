package jcolin.commands.custom;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command;
import jcolin.commands.Container;

public class CustomContainer extends Container {
    private String m_description;
    private Collection<Command> m_options;
    
    public CustomContainer(String[] names) { 
        super(names); 
        m_options = new ArrayList<Command>();
    }
    
    public CustomContainer(String[] names, Command command) { 
        super(names, command); 
    }

    public Collection<Command> getSubCommands() { 
        return m_options; 
    }
    
    protected String getDescription() { 
        return m_description; 
    }
    
    protected Command clone(Command command) { 
        return new CustomContainer(names(), command); 
    }
    
    public void setDescription(String description) {
        m_description = description;
    }
    
    public void addCommand(Command command) {
        m_options.add(command);
    }   
}
