package jcolin.commands.source;

import java.io.IOException;
import java.io.Writer;

import jcolin.consoles.Console;

public class ScriptConsoleWriter extends Writer {
	private Console m_console;
	private StringBuffer m_buffer;
	
	public ScriptConsoleWriter(Console console) {
		m_console = console;
		m_buffer = new StringBuffer();
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
		m_buffer.append(stringBuffer.toString());
	}
	
	public String getScriptOutput() {
		return m_buffer.toString().trim();
	}
}
