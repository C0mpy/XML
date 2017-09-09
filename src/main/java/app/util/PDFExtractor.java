package app.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDFExtractor {
	
	public static final String FONT = "src/main/resources/fonts/FreeSans.ttf";
	
	public static void extract(String data, String filePath) throws IOException, DocumentException {
		
		// Step 1
    	Document document = new Document();
    	
    	// Step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        // Step 3
        document.open();
        
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        InputStream is = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        
        worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"), fontImp);
        
        // Step 5
        document.close();
        
	}

}
