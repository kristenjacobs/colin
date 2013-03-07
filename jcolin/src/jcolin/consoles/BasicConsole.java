package jcolin.consoles;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;

public class BasicConsole extends Console {

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

    @SuppressWarnings("resource")
    @Override
    public String getNextLine(Vector<Command> commandHistory) {
        return new Scanner(System.in).nextLine();
    }

    /**
     * Note: The 'forceOutputDisplay' parameter ensure that the output gets
     * displayed on the console irrespective of the settings of the command that
     * initiated the output.
     * 
     * Note: The 'addToLog' parameter can be used to enable/disable the storing
     * of this output string in the output buffer.
     */
    private void output(String str, boolean forceOutputDisplay, boolean addToLog) {
        super.addCommandOutput(str, addToLog);
        if (outputEnabled(forceOutputDisplay) &&
            !redirectToFile(str)) {
            System.out.printf("%s", str);
        }       
    }
}
