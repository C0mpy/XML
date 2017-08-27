package app.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;

import app.jaxb_model.Act;
import app.util.MarklogicProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

@Component
public class ActRepository {

	public void save(Act act) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
	
		JAXBContext context = JAXBContext.newInstance(Act.class);
		JAXBHandle<Act> handle = new JAXBHandle<>(context);
		handle.set(act);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		docMgr.write("/acts/" + act.getId(), handle);
		
		client.release();
	}

}
