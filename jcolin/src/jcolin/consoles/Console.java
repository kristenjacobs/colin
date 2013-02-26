package jcolin.consoles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jcolin.commands.Command;
import jcolin.commands.CommandFactory;
import jcolin.commands.Command.RedirectMode;

public abstract class Console implements IConsole {
	
	public enum OutputMode {
		ALL,
		INTERNAL,
		EXTERNAL,
		NONE,
	}
	
	public enum Context {
		EXTERNAL,
		INTERNAL,
	}
	
	private PrintWriter m_fileWriter;
	private StringBuffer m_outputBuffer;
	private OutputMode m_outputMode;
	private Context m_context;

	public abstract void initialise(CommandFactory commandFactory) throws IOException;
	public abstract void displayPrompt(String str);
	public abstract String getNextLine(Vector<Command> commandHistory);

	public Console() {
		m_outputBuffer = new StringBuffer();
		m_outputMode = OutputMode.ALL;
		m_context = Context.EXTERNAL;
	}
	
	public boolean redirectToFile(String str) {
		if (m_fileWriter != null) {
			m_fileWriter.printf("%s", str);
			return true;
		}
		return false;
	}

	public boolean openRedirectFile(String fileName, RedirectMode redirectMode) {
		assert(m_fileWriter == null);
		try {
			switch (redirectMode) {
			case OVERWRITE:
				m_fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
				return true;

			case APPEND:
				m_fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
				return true;

			default:
				assert(false);
				return false;
			}

		} catch (IOException e) {
			displayError("Unable to open file for writing: " + fileName + "\n");
			m_fileWriter = null;
			return false;
		}
	}

	public void closeRedirectFile() {
		if (m_fileWriter != null) {
			m_fileWriter.close();
			m_fileWriter = null;			
		}
	}
	
	public void clearCommandOutput() {
		m_outputBuffer = new StringBuffer();
	}
	
	public void addCommandOutput(String output) {
		m_outputBuffer.append(output);
	}
	
	public String getCommandOutput() {
		return m_outputBuffer.toString().trim();		
	}
	
	public void setOutputMode(OutputMode outputMode) {
		m_outputMode = outputMode;
	}
	
    public OutputMode getOutputMode() {
    	return m_outputMode;
    }
        
    public void setContext(Context context) {
    	m_context = context;
    }
    
    public Context getContext() {
    	return m_context;
    }    

	boolean outputEnabled(boolean forceOutputDisplay) {
		if (forceOutputDisplay)
			return true;
		
		switch (getOutputMode()) {
		case ALL:
			return true;

		case EXTERNAL:
			return getContext() == Context.EXTERNAL;
			
		case INTERNAL:
			return getContext() == Context.INTERNAL;
			
		case NONE:
			return false;	
			
		default:
			assert(false);
			return true;
		}
	}
}
