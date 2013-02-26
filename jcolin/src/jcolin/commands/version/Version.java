package jcolin.commands.version;

import jcolin.commands.Command;
import jcolin.consoles.Console;
import jcolin.shell.Shell;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Version extends Command {
    private static final String[] COMMAND_NAMES = { "version" };

    public Version() {
        super(COMMAND_NAMES);
    }

    public int numArgs() {
        return 0;
    }

    public String commandLine() {
        return name();
    }

    public String description(boolean detailed) {
        return name() + ": Displays the version information";
    }

    public Command clone(String[] args, int index) {
        return new Version();
    }

    public void execute(Shell shell, Object model, Console console) {
        String versionString = 
                "Tool: " + shell.getToolName() +
                ", Version: " + shell.getVersion();
        console.display(versionString + "\n");
    }

    static class XMLHandler extends DefaultHandler {
        String m_versionString;

        public XMLHandler() {
            super();
            m_versionString = null;
        }

        public void startElement (String uri, String name,
                  String qName, Attributes atts) {
            String nameString = getAttributeValue(atts, "name");
            if (nameString != null) {
                if (nameString.equals("aboutText")) {
                    String valueString = getAttributeValue(atts, "value");
                    if (valueString != null) {
                        m_versionString = valueString;
                    }
                }
            }
        }

        public String getVersionString() {
            return m_versionString;
        }

        private String getAttributeValue(Attributes atts, String name) {
            for (int index = 0; index < atts.getLength(); ++index) {
                if (atts.getLocalName(index).equals(name)) {
                    return atts.getValue(index);
                }
            }
            return null;
        }
    }
}
