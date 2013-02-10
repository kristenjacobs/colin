package jcolin.commands.source;

import java.io.IOException;
import java.io.Writer;

import jcolin.consoles.Console;

public class ScriptConsoleWriter extends Writer {
	private Console m_console;
	
	public ScriptConsoleWriter(Console console) {
		m_console = console;
	}
	
	public void close() throws IOException {
	}

	public void flush() throws IOException {
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = off; i < off + len; ++i) {
			stringBuffer.append(cbuf[i]);		
	    }
		m_console.display(stringBuffer.toString());
	}
}
