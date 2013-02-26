package jcolin.utils;

import java.io.File;
import java.io.StringReader;

import jcolin.consoles.IConsole;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.SinglePropertyMap;
import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.auto.AutoSchemaReader;

public class RngUtils {

    public static boolean validate(File xmlFile, File rngFile, IConsole console) throws Exception {
        return validate(ValidationDriver.fileInputSource(xmlFile),
                 ValidationDriver.fileInputSource(rngFile),
                 new VerboseErrorHandler(console)); 
    }

    public static boolean validate(String xmlStr, String rngStr) throws Exception {     
        return validate(new InputSource(new StringReader(xmlStr)),
                 new InputSource(new StringReader(rngStr)),
                 new VerboseErrorHandler(null)); 
    }
    
    private static boolean validate(InputSource inXml, InputSource inRng, 
            ErrorHandler errorHandler) throws Exception {
        
        SchemaReader sr = new AutoSchemaReader();
        ValidationDriver driver = new ValidationDriver(getPropertyMap(errorHandler), sr);
        inRng.setEncoding("UTF-8");
        driver.loadSchema(inRng);       
        inXml.setEncoding("UTF-8");
        return driver.validate(inXml);                  
    }
    
    private static PropertyMap getPropertyMap(ErrorHandler errorHandler) {
        return SinglePropertyMap.newInstance(ValidateProperty.ERROR_HANDLER, errorHandler);
    }
    
    private static class VerboseErrorHandler implements ErrorHandler {
        private IConsole m_console;
        
        public VerboseErrorHandler(IConsole console) {
            m_console = console;
        }       
        @Override
        public void warning(SAXParseException exception)
                throws SAXException {
            if (m_console != null) {
                m_console.displayWarning(exception.getMessage() + "\n");                
            }
        }
        @Override
        public void error(SAXParseException exception)
                throws SAXException {
            if (m_console != null) {
                m_console.displayError(exception.getMessage() + "\n");              
            }
        }
        @Override
        public void fatalError(SAXParseException exception)
                throws SAXException {
            if (m_console != null) {
                m_console.displayError(exception.getMessage() + "\n");              
            }
        }           
    };
}
