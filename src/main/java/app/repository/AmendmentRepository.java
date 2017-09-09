package app.repository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPage;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentRecord;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;
import com.marklogic.client.util.EditableNamespaceContext;

import app.jaxb_model.Amendment;
import app.util.MarklogicProperties;
import app.util.MetadataExtractor;

@Component
public class AmendmentRepository {
	
	private static String XSLT_FILE = "src/main/resources/xsl/amendment_to_rdf.xsl";
	
	public void save(Amendment amendment) throws JAXBException, UnsupportedEncodingException, TransformerException {
			
			@SuppressWarnings("deprecation")
			DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
					MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
			XMLDocumentManager docMgr = client.newXMLDocumentManager();
			
			// create new context bound to the Amendment class
			JAXBContext context = JAXBContext.newInstance(Amendment.class);
			
			// create handle for xml content
			JAXBHandle<Amendment> contentHandle = new JAXBHandle<>(context);
			contentHandle.set(amendment);
			
			// add amendment in a collection so we can easier query against them later
			DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
			metadataHandle.getCollections().add("/amendments/collections");
			
			// write data to db
			docMgr.write("/amendments/" + amendment.getId(), metadataHandle, contentHandle);
			
			// parse metadata from attributes in xml file to a rdf file
			String metadata = MetadataExtractor.extract(contentHandle.toString(), XSLT_FILE);
						
			// add rdf triplet to the database
			GraphManager graphManager = client.newGraphManager();
	        StringHandle stringHandle = new StringHandle(metadata).withMimetype(RDFMimeTypes.RDFXML);
	        graphManager.merge("amendments/metadata", stringHandle);

			client.release();
			
		}
	
	public void setStatus(String amendmentId, String status) {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// used to change existing document's data
		DocumentPatchBuilder builder = docMgr.newPatchBuilder();
		
		// set namespace so we can use it in patch builder
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("am", "www.assembly.gov.rs/amendments/");
        builder.setNamespaces(namespaces);
		
        // set status attribute
     	builder.replaceValue("/am:amendment/@status", status);
		
     	// save to database
     	docMgr.patch("/amendments/" + amendmentId, builder.build());
     	
     	// update rdf triple with the new status value
     	SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
        String subject = "www.assembly.gov.rs/amendments/" + amendmentId;
        String predicate = "www.assembly.gov.rs/amendments/status";
        updateTriplet(sparqlQueryManager, "amendments/metadata", subject, predicate, status);
		
		client.release();
		
	}
	
	// update metadata in rdf triplet in marklogic database
    public void  updateTriplet(SPARQLQueryManager manager, String metadataURI, String subject, String predicate, String object) {
    	
    	// in metadataURI delete triple with subject + predicate combination specified
    	// then insert a new one with the set object
        String queryDefinition =
                        " WITH <" + metadataURI + ">" +
                        " DELETE { <" + subject + "> <" + predicate + ">  ?o} " +
                        " INSERT { <" + subject + "> <" + predicate + "> \"" + object + "\" }" +
                        " WHERE  { <" + subject + "> <" + predicate + ">  ?o}";
               
        SPARQLQueryDefinition query = manager.newQueryDefinition(queryDefinition);
        manager.executeUpdate(query);
        
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
	
    public ArrayList<String> findAll() throws JAXBException {
    	
    	@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
    	
    	XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
    	// empty query - fetch all
    	QueryManager qm = client.newQueryManager();
    	
    	// limit search to all documents that are in /acts/collections
    	StringQueryDefinition query =  qm.newStringDefinition();
    	query.setCollections("/amendments/collections");
    	
    	DocumentPage documents = docMgr.search(query, 1);
    	ArrayList<String> amendments = new ArrayList<String>();
    	// add each result to the array
    	while (documents.hasNext()) {
    	    DocumentRecord document = documents.next();
    	    StringHandle handle = new StringHandle();
    	    amendments.add(document.getContent(handle).toString());
    	}
    	
    	return amendments;
    	
    }
    
    // export amendments metadata to json string
    public String metadataToJSON() {
    	
    	@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
    	
    	 GraphManager graphManager = client.newGraphManager();
         String content = graphManager.read("amendments/metadata", new StringHandle().withMimetype(RDFMimeTypes.RDFJSON)).withFormat(Format.JSON).get();
         client.release();

         return content;
    	
    }

}