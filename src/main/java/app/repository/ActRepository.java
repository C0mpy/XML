package app.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.util.EditableNamespaceContext;
import com.marklogic.client.io.Format;

import app.jaxb_model.Act;
import app.jaxb_model.Operation;
import app.jaxb_model.Target;
import app.util.MarklogicProperties;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Component;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

@Component
public class ActRepository {
	
	private static final String XSLT_FILE = "src/main/resources/xsl/xml_to_rdf.xsl";

	public void save(Act act, String sessionId) throws JAXBException, UnsupportedEncodingException, TransformerException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
	
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// create new context bound to the Act class
		JAXBContext context = JAXBContext.newInstance(Act.class);
		
		// create handle for xml content
		JAXBHandle<Act> contentHandle = new JAXBHandle<>(context);
		contentHandle.set(act);
        
		// parse metadata from attributes in xml file to a rdf file
		String metadata = getMetadata(contentHandle);
		
		// add act in a collection so we can easier query against them later
		DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
		metadataHandle.getCollections().add("/acts/collections");
		
		// write data to db
		docMgr.write("/acts/" + act.getId(), metadataHandle, contentHandle);
		
		// add rdf triplet to the database
		GraphManager graphManager = client.newGraphManager();
        StringHandle stringHandle = new StringHandle(metadata).withMimetype(RDFMimeTypes.RDFXML);
        graphManager.merge("/acts/metadata", stringHandle);

		client.release();
	}

	
	public String getMetadata(JAXBHandle<Act> contentHandle) throws UnsupportedEncodingException, TransformerException {
		
		// Create transformation source
		StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
		
		// Initialize GRDDL transformer object
		TransformerFactory factory = new TransformerFactoryImpl();
		Transformer transformer = factory.newTransformer(transformSource);
		
		// Set the indentation properties
		transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		
		// Initialize transformation subject
		InputStream in = new ByteArrayInputStream(contentHandle.toString().getBytes(StandardCharsets.UTF_8.name()));
		StreamSource source = new StreamSource(in);
		
		// Initialize result stream
		StringWriter out = new StringWriter();
		StreamResult result = new StreamResult(out);
		
		transformer.transform(source, result);
		
		return out.toString();
		
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
