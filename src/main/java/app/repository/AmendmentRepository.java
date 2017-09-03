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
			
			// create handle xml content
			JAXBHandle<Amendment> handle = new JAXBHandle<>(context);
			handle.set(amendment);
			
			DocumentMetadataHandle metadataHandle = getMetadata(amendment);
			
			docMgr.write("/amendments/" + amendment.getId(), metadataHandle, handle);
			
			client.release();
			
		}
	
	public void withdraw(String amendmentId) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// used to change existing document's metadata
		DocumentMetadataPatchBuilder builder = docMgr.newPatchBuilder(Format.XML);
		
		// set metadata value
		builder.addMetadataValue("status", "CANCELED");
		
		// save to database
		docMgr.patch("/amendments/" + amendmentId, builder.build());
		
		client.release();
		
	}
	
	// extract metadata from Object and put it in DocumentMetadataHandle
	private DocumentMetadataHandle getMetadata(Amendment amendment) {
		
		DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
		
		metadataHandle.withMetadataValue("id", amendment.getId());
		metadataHandle.withMetadataValue("actId", amendment.getActId());
		metadataHandle.withMetadataValue("status", amendment.getStatus().toString());
		
		return metadataHandle;
	}
}