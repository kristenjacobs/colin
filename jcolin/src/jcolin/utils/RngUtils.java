package jcolin.utils;

import java.io.File;
import java.io.StringReader;

import org.xml.sax.InputSource;

import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.auto.AutoSchemaReader;

public class RngUtils {

	public static boolean validate(File xmlFile, File rngFile) throws Exception {
		return validate(ValidationDriver.fileInputSource(xmlFile),
				 ValidationDriver.fileInputSource(rngFile)); 
	}

	public static boolean validate(String xmlStr, String rngStr) throws Exception {		
		return validate(new InputSource(new StringReader(xmlStr)),
			     new InputSource(new StringReader(rngStr))); 
	}
	
	private static boolean validate(InputSource inXml, InputSource inRng) throws Exception {			
	    SchemaReader sr = new AutoSchemaReader();
		ValidationDriver driver = new ValidationDriver(sr);
		inRng.setEncoding("UTF-8");
		driver.loadSchema(inRng);
		inXml.setEncoding("UTF-8");
		return driver.validate(inXml);		    		
	}	
}
