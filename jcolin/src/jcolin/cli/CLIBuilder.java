package jcolin.cli;

import java.io.File;

import jcolin.commands.custom.Argument;
import jcolin.commands.custom.CustomCommand;
import jcolin.commands.custom.CustomContainer;
import jcolin.consoles.IConsole;
import jcolin.consoles.Console.OutputMode;
import jcolin.utils.DomUtils;
import jcolin.utils.RngUtils;
import jcolin.validators.ValidatorFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CLIBuilder {
	private IConsole m_console;
	
	public CLIBuilder(IConsole console) {
		m_console = console;
	}

	public CLI build() throws Exception {
	    File configFile = getConfigFile();
	    if (configFile.exists()) {
			if (!validateConfigFile(configFile)) {
				throw new Exception("Validation Failed");
			}
	        Document doc = DomUtils.createDocument(configFile);
			CLI cli = createCLI(doc);
			addCommandsToCLI(cli, doc, "commands");
			addCommandsToCLI(cli, doc, "extensions");			
			addTestCasesToCLI(cli, doc);		
			return cli;
			
        } else {
    	    m_console.displayWarning("config.xml file not found\n");
    		return new CLI("jcolin", ">", "????");
        }
	}

    private File getConfigFile() {
    	// To find the config file we first look for the required system property    	    
	    String configFile = System.getProperty("ConfigFile");
	    if (configFile != null) {
		    return new File(configFile);
	    }
	    // Next see if the required environment variable is set..
	    configFile = System.getenv("JCOLIN_CONFIG_FILE");
	    if (configFile != null) {
		    return new File(configFile);
	    }
	    // Otherwise, default to looking for a 'config.xml' in the current directory. 
	    return new File("config.xml");
    }

	private boolean validateConfigFile(File configFile) throws Exception {		
		File schemaFile = getSchemaFile();
		if (schemaFile.exists()) {
	        return RngUtils.validate(configFile, schemaFile, m_console);
	        
	    } else {
	    	m_console.displayWarning("Schema not found, unable to validate the config.xml\n");
			return true;
	    }			
	}

    private File getSchemaFile() {
    	// To find the schema file we first look for the required system property    	    
	    String schemaFile = System.getProperty("SchemaFile");
	    if (schemaFile != null) {
		    return new File(schemaFile);
	    }
	    // Next see if the required environment variable is set..
	    schemaFile = System.getenv("JCOLIN_SCHEMA_FILE");
	    if (schemaFile != null) {
		    return new File(schemaFile);
	    }
	    // Otherwise, default to looking for a 'config.rng' in the current directory. 
	    return new File("config.rng");
    }

    private CLI createCLI(Document doc) {
        NodeList interfaceNodes = doc.getElementsByTagName("colin");
        Node interfaceNode = interfaceNodes.item(0);
        Element interfaceElement = (Element)interfaceNode;
        String toolname = interfaceElement.getAttribute("toolname");
        String prompt   = interfaceElement.getAttribute("prompt");                
        String version  = interfaceElement.getAttribute("version");
		return new CLI(toolname, prompt, version);
    }
    
	private void addCommandsToCLI(CLI cli, Document doc, String nodeName) throws Exception {
        NodeList commandsNodes = doc.getElementsByTagName(nodeName);
        if (commandsNodes.getLength() > 0) {
            Node commandsNode = commandsNodes.item(0);
            NodeList children = commandsNode.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child instanceof Element) {
                	Element element = (Element)child;
        
                	if (element.getNodeName().equals("command")) {
                		handleCommandElement(cli, element);
                                        		
                	} else if (element.getNodeName().equals("container")) {
                		handleContainerElement(cli, element);
                	}
                }
            }            	
        }
	}
	
	private void addTestCasesToCLI(CLI cli, Document doc) {		
		// TODO
	}
	
	private void handleCommandElement(CLI cli, Element element) throws Exception {
        cli.addCommand(createCustomCommand(element));		
	}
	
	private void handleContainerElement(CLI cli, Element element) throws Exception {
    	String name  = element.getAttribute("name");
    	CustomContainer container = new CustomContainer(name.split(","));    	

    	String description = getElement(element, "description").getTextContent();        
    	container.setDescription(description);

    	NodeList commandNodes = element.getElementsByTagName("command");
        for (int i = 0; i < commandNodes.getLength(); i++) {
            Node commandNode = commandNodes.item(i);
            if (commandNode instanceof Element) {
            	container.addCommand(createCustomCommand((Element)commandNode));		            	
            }
        }    
        cli.addCommand(container);        			
	}
	
	private CustomCommand createCustomCommand(Element element) throws Exception {
    	String name = element.getAttribute("name");
    	String impl = element.getAttribute("impl");  
            
        CustomCommand command = new CustomCommand(name.split(","), impl);
        
    	String description = getElement(element, "description").getTextContent();        
        command.setDescription(description);

        Element argsElement = getElement(element, "args");
        if (argsElement != null) {
            NodeList argNodes = argsElement.getElementsByTagName("arg");
            for (int i = 0; i < argNodes.getLength(); i++) {
                Node argNode = argNodes.item(i);
                if (argNode instanceof Element) {                	
                	command.addArgument(createArgument((Element)argNode));
                }
            }                	
        }
        
        Element outputElement = getElement(element, "output");
        if (outputElement != null) {
        	
        	if (outputElement.hasAttribute("display")) {
        		if (outputElement.getAttribute("display").equals("ALL")) {
        			command.setOutputMode(OutputMode.ALL);
        			
        		} else if (outputElement.getAttribute("display").equals("INTERNAL")) {
        			command.setOutputMode(OutputMode.INTERNAL);
        			
        		} else if (outputElement.getAttribute("display").equals("EXTERNAL")) {
        			command.setOutputMode(OutputMode.EXTERNAL);
        			
        		} else if (outputElement.getAttribute("display").equals("NONE")) {
        			command.setOutputMode(OutputMode.NONE);
        		}
        	}
        
            NodeList outputNodes = outputElement.getChildNodes(); 
            for (int i = 0; i < outputNodes.getLength(); i++) {
                Node outputNode = outputNodes.item(i);
                if (outputNode instanceof Element) {                	
               		command.addOutputValidator(ValidatorFactory.create((Element)outputNode));                		
                }
            }
        }        
     
        return command;
	}

	private Argument createArgument(Element element) throws Exception {
    	String argName = element.getAttribute("name");
    	Argument argument = new Argument(argName);		
    	
        NodeList validatorNodes = element.getChildNodes();
        for (int i = 0; i < validatorNodes.getLength(); i++) {
            Node validatorNode = validatorNodes.item(i);
            if (validatorNode instanceof Element) {       
           		argument.addValidator(ValidatorFactory.create((Element)validatorNode));
            }
        }                	
    	return argument;		
	}
		
    private Element getElement(Element element, String name) {
        NodeList nodes = element.getElementsByTagName(name);
        Node node = nodes.item(0);
        return (Element)node; 	
    }    
}
