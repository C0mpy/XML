package app.repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;

import app.jaxb_model.Amendment;
import app.util.MarklogicProperties;

@Component
public class AmendmentRepository {
	
public void save(Amendment amendment) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
	
		JAXBContext context = JAXBContext.newInstance(Amendment.class);
		JAXBHandle<Amendment> handle = new JAXBHandle<>(context);
		handle.set(amendment);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		docMgr.write("/amendments/" + amendment.getId(), handle);
		
		client.release();
	}

}
