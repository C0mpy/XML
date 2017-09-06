package app.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;
import com.marklogic.client.io.Format;

import app.jaxb_model.Act;
import app.jaxb_model.Data;
import app.jaxb_model.Operation;
import app.jaxb_model.Part;
import app.jaxb_model.Status;
import app.jaxb_model.Target;
import app.util.MarklogicProperties;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
	
	public void update(String actId, Target target) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// this has to be used because elements (PART, CHAPTER...) are in act schema
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("a", "www.assembly.gov.rs/acts");
		
		DocumentPatchBuilder builder = docMgr.newPatchBuilder();
		builder.setNamespaces(namespaces);
		
		// xpath query that finds all items (on any depth) with the specified id value:  //*[@id={id_value}]
		if(target.getOperation() == Operation.DELETE) {
			builder.delete("//*[@id='" + target.getTargetId() + "']");
		}
		else if(target.getOperation() == Operation.UPDATE) {
			String d = "";
			for(Serializable s : target.getContent().getContent()) {
				d += s + "\n";
			}
			builder.replaceFragment("//*[@id='" + target.getTargetId() + "']", d);
		}
		else if(target.getOperation() == Operation.INSERT) {
			String xml = "";
			Object targetObject = getTargetElement(target);
			xml = toXML(targetObject);
			System.out.println(target.getPosition());
			System.out.println(xml);
			builder.insertFragment("//*[@id='" + target.getTargetId() + "']", Position.valueOf(target.getPosition().toString()), xml);
		}
	
		DocumentPatchHandle handle = builder.build();
		docMgr.patch("/acts/" + actId, handle);
		client.release();
		
	}
	
	
	// fetch the correct attribute from the target object
	private Object getTargetElement(Target target) {
		if(target.getType().equals("PART")) {
			return target.getPart();
		}
		else if(target.getType().equals("CHAPTER")) {
			return target.getChapter();
		}
		else if(target.getType().equals("SECTION")) {
			return target.getSection();
		}
		else if(target.getType().equals("SUBSECTION")) {
			return target.getSubsection();
		}
		else if(target.getType().equals("ARTICLE")) {
			return target.getArticle();
		}
		else if(target.getType().equals("PARAGRAPH")) {
			return target.getParagraph();
		}
		else if(target.getType().equals("CLAUSE")) {
			return target.getClause();
		}
		else if(target.getType().equals("SUBCLAUSE")) {
			return target.getSubclause();
		}
		else if(target.getType().equals("INDENT")) {
			return target.getIndent();
		}
		else {
			return target.getContent();
		}
	}
	
    private String toXML(Object object) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JAXB.marshal(object, stream);
        String xml = stream.toString();
        
        // remove first line of xml, marshal generates <?xml version="1.0" encoding="UTF-8" standalone="yes"?> and its excess and produces an error
        xml = xml.substring(xml.indexOf('\n')+1);
        return xml;
    }

}
