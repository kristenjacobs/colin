package jcolin.consoles;

import java.io.IOException;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;

public class TestConsole extends Console {
    private StringBuffer m_testBuffer;
    
    public TestConsole() {
        m_testBuffer = new StringBuffer();
    }
    
    public void initialise(CommandFactory commandFactory) throws IOException {  
    }
    
    public void display(String str) {
        output(str, false, true);
    }

    public void displayError(String str) {
        output("error: " + str, true, false);
    }

    public void displayWarning(String str) {
        output("warning: " + str, true, false);        
    }
    
    public void displayInfo(String str) {
        output("info: " + str, true, false);
    }
    
    public void displayPrompt(String str) {
        System.out.printf("%s", str);
    }

    @Override
    public String getNextLine(Vector<Command> commandHistory) {
        assert(false);
        return null;
    }
    
    public String getTestOutput() {
        return m_testBuffer.toString().trim();
    }
    
    public void clearTestOutput() {
        m_testBuffer.delete(0, m_testBuffer.length());
    }
    
    private void output(String str, boolean forceOutputDisplay, boolean addToLog) {
        super.addCommandOutput(str, addToLog);
        if (outputEnabled(forceOutputDisplay)) {
            m_testBuffer.append(str);           
        }
    }
}
