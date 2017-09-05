package app.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.Format;

import app.jaxb_model.Act;
import app.jaxb_model.Status;
import app.util.MarklogicProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

@Component
public class ActRepository {

	public void save(Act act, String sessionId) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
	
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// create new context bound to the Act class
		JAXBContext context = JAXBContext.newInstance(Act.class);
		
		// create handle for xml content
		JAXBHandle<Act> contentHandle = new JAXBHandle<>(context);
		contentHandle.set(act);
		
		// create handle for metadata
		DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
		metadataHandle.withMetadataValue("id", act.getId());
		metadataHandle.withMetadataValue("status", Status.SCHEDULED.toString());
		metadataHandle.withMetadataValue("sessionId", sessionId);
	
		docMgr.write("/acts/" + act.getId(), metadataHandle, contentHandle);
		
		client.release();
	}
	
	public void setStatus(String actId, String status) {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// used to change existing document's metadata
		DocumentMetadataPatchBuilder builder = docMgr.newPatchBuilder(Format.XML);
		
		// set metadata value
		builder.addMetadataValue("status", status);
		
		// save to database
		docMgr.patch("/acts/" + actId, builder.build());
		
		client.release();
		
	}

}
