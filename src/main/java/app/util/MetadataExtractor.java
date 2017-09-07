package app.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

public class MetadataExtractor {

	public static String extract(String content, String XSLTFile) throws UnsupportedEncodingException, TransformerException {
		
		// Create transformation source
		StreamSource transformSource = new StreamSource(new File(XSLTFile));
		
		// Initialize GRDDL transformer object
		TransformerFactory factory = new TransformerFactoryImpl();
		Transformer transformer = factory.newTransformer(transformSource);
		
		// Set the indentation properties
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		
		// Initialize transformation subject
		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8.name()));
		StreamSource source = new StreamSource(in);
		
		// Initialize result stream
		StringWriter out = new StringWriter();
		StreamResult result = new StreamResult(out);
		
		transformer.transform(source, result);
		
		return out.toString();
		
	}

}
