package jcolin.cli;

import java.util.ArrayList;
import java.util.Collection;

import jcolin.commands.Command;
import jcolin.testing.Testcase;

public class CLI {
    private String m_toolName;
    private String m_prompt;
    private String m_version;
    private Collection<Command> m_commands;
    private Collection<Testcase> m_testcases;
    
    CLI(String toolName, String prompt, String version) {
        m_toolName = toolName;
        m_prompt = prompt;
        m_version = version;
        m_commands = new ArrayList<Command>();
        m_testcases = new ArrayList<Testcase>();
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

    public Collection<Testcase> getTestcases() {
        return m_testcases;
    }

    void addCommand(Command command) {
        m_commands.add(command);
    }
    
    void addTestcase(Testcase testcase) {
        m_testcases.add(testcase);
    }
}
