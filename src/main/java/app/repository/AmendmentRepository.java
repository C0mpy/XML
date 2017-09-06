package app.repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.JAXBHandle;

import app.jaxb_model.Amendment;
import app.util.MarklogicProperties;

@Component
public class AmendmentRepository {
	
	public void save(Amendment amendment) throws JAXBException {
			
			@SuppressWarnings("deprecation")
			DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
					MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
			XMLDocumentManager docMgr = client.newXMLDocumentManager();
			
			// create new context bound to the Act class
			JAXBContext context = JAXBContext.newInstance(Amendment.class);
			
			// create handle for xml content
			JAXBHandle<Amendment> handle = new JAXBHandle<>(context);
			handle.set(amendment);
			
			// create handle for metadata
			DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
			metadataHandle.withMetadataValue("id", amendment.getId());
			metadataHandle.withMetadataValue("actId", amendment.getActId());
			metadataHandle.withMetadataValue("status", amendment.getStatus().toString());
			
			docMgr.write("/amendments/" + amendment.getId(), metadataHandle, handle);
			
			client.release();
			
		}
	
	public void setStatus(String amendmentId, String status) {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// used to change existing document's metadata
		DocumentMetadataPatchBuilder builder = docMgr.newPatchBuilder(Format.XML);
		
		// set metadata value
		builder.addMetadataValue("status", status);
		
		// save to database
		docMgr.patch("/amendments/" + amendmentId, builder.build());
		
		client.release();
		
	}
	
	public Amendment findOne(String amendmentId) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
	    final JAXBContext context = JAXBContext.newInstance(Amendment.class);
	    
        final JAXBHandle<Amendment> handle = new JAXBHandle<>(context);
        
        docMgr.read("/amendments/" + amendmentId, handle);
        final Amendment amendment = handle.get();

		client.release();
		
		return amendment;
	}
	
	public void applyAmendmentToAct() {
		
	}

}