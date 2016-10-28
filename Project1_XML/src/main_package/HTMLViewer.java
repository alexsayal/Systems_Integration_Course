package main_package;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class HTMLViewer {

	public static void main(String[] args) throws IOException {
		try {
			System.out.println("|| ========== HTML VIEWER ========== ||");
			
			// Transformação
			TransformerFactory tFactory=TransformerFactory.newInstance();

			Source xslDoc=new StreamSource("src/Files/Final_Output.xsl");
			Source xmlDoc=new StreamSource("src/Files/Final_Output.xml");

			String outputFileName="src/Files/Interests.html";

			OutputStream htmlFile=new FileOutputStream(outputFileName);
			Transformer transform=tFactory.newTransformer(xslDoc);
			transform.transform(xmlDoc, new StreamResult(htmlFile));
			
			// Abrir automaticamente o browser
			File htmlFileRun = new File(outputFileName);
			Desktop.getDesktop().browse(htmlFileRun.toURI());
			System.out.println("Interests.html file created.");
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (TransformerConfigurationException e) 
		{
			e.printStackTrace();
		}
		catch (TransformerFactoryConfigurationError e) 
		{
			e.printStackTrace();
		}
		catch (TransformerException e) 
		{
			e.printStackTrace();
		}
	}
}