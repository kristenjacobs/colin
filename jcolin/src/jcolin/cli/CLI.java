package jcolin.cli;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command;

public class CLI {
    private String m_toolName;
    private String m_prompt;
    private String m_version;
    private Collection<Command> m_commands;
    
    CLI(String toolName, String prompt, String version) {
    	m_toolName = toolName;
    	m_prompt = prompt;
    	m_version = version;
    	m_commands = new ArrayList<Command>();
    }
    
    public String getToolName() {
    	return m_toolName;
    }
    
    public String getPrompt() {
    	return m_prompt;
    }

    public String getVersion() {
    	return m_version;
    }
    
    public Collection<Command> getCommands() {
    	return m_commands;
    }
    
    void addCommand(Command command) {
    	m_commands.add(command);
    }
}
