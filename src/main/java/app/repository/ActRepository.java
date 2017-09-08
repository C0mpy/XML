package app.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.impl.SPARQLBindingsImpl;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.semantics.SPARQLBindings;
import com.marklogic.client.semantics.SPARQLMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;
import com.marklogic.client.util.EditableNamespaceContext;

import app.jaxb_model.Act;
import app.jaxb_model.Amendment;
import app.jaxb_model.Operation;
import app.jaxb_model.Paragraph;
import app.jaxb_model.Target;
import app.util.MarklogicProperties;
import app.util.MetadataExtractor;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.MatchResult;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.stereotype.Component;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

@Component
public class ActRepository {
	
	private static String XSLT_FILE = "src/main/resources/xsl/act_to_rdf.xsl";

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
		
		// add act in a collection so we can easier query against them later
		DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
		metadataHandle.getCollections().add("/acts/collections");
		
		// write data to db
		docMgr.write("/acts/" + act.getId(), metadataHandle, contentHandle);
		
		// parse metadata from attributes in xml file to a rdf file
		String metadata = MetadataExtractor.extract(contentHandle.toString(), XSLT_FILE);
				
		// add rdf triplet to the database
		GraphManager graphManager = client.newGraphManager();
        StringHandle stringHandle = new StringHandle(metadata).withMimetype(RDFMimeTypes.RDFXML);
        graphManager.merge("acts/metadata", stringHandle);

		client.release();
	}
	
	public void setStatus(String actId, String status) {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// used to change existing document's data
		DocumentPatchBuilder builder = docMgr.newPatchBuilder();
		
		// set namespace so we can use it in patch builder
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
        namespaces.put("a", "www.assembly.gov.rs/acts/");
        builder.setNamespaces(namespaces);
		
        // set status attribute
		builder.replaceValue("/a:act/@status", status);
		
		// save to database
		docMgr.patch("/acts/" + actId, builder.build());
		
		// update rdf triple with the new status value
		SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
        String subject = "www.assembly.gov.rs/acts/" + actId;
        String predicate = "www.assembly.gov.rs/acts/status";
        updateTriplet(sparqlQueryManager, "acts/metadata", subject, predicate, status);

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
	
	public void update(String actId, Target target) throws JAXBException {
		
		@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
		
		XMLDocumentManager docMgr = client.newXMLDocumentManager();
		
		// this has to be used because elements (PART, CHAPTER...) are in act schema
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("a", "www.assembly.gov.rs/acts/");
		
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
		else if(target.getType().toString().equals("PARAGRAPH")) {
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
	
	// marshal object to xml string
    private String toXML(Object object) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JAXB.marshal(object, stream);
        String xml = stream.toString();
        
        // remove first line of xml, marshal generates <?xml version="1.0" encoding="UTF-8" standalone="yes"?> and its excess and produces an error
        xml = xml.substring(xml.indexOf('\n')+1);
        return xml;
    }
    
    public ArrayList<String> findByText(String text) throws JAXBException {
    	
    	@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
    	
    	QueryManager queryMgr = client.newQueryManager();
    	
    	// query object
    	StringQueryDefinition qd = queryMgr.newStringDefinition();
    	
    	// limit search to acts
    	qd.setCollections("/acts/collections");
    	
    	// find all acts that contain 'text' string within
    	qd.setCriteria(text);
    	SearchHandle results = queryMgr.search(qd, new SearchHandle());
    	
    	ArrayList<String> acts = new ArrayList<String>();
    	
    	// iterate all act's id's that fit the search, find acts in database by id and add them to the list
    	for(MatchDocumentSummary mr : results.getMatchResults()) {
    		String[] parts = mr.getUri().split("/");
    		String id = parts[2];
    		Act act = findById(client, id);
    		acts.add(toXML(act));
    	}
    	
    	return acts;
    
    }
    
    public ArrayList<String> findByTerm(String pred, String obj) throws JAXBException {
    	
    	@SuppressWarnings("deprecation")
		DatabaseClient client = DatabaseClientFactory.newClient(MarklogicProperties.HOST, MarklogicProperties.PORT, MarklogicProperties.DATABASE,
				MarklogicProperties.USER, MarklogicProperties.PASS, DatabaseClientFactory.Authentication.DIGEST);
    	
    	// Create a SPARQL query manager to query RDF datasets
    	SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
    	
    	// Initialize SPARQL query definition - retrieves all triples from RDF dataset 
    	// obj must be inside "" because its literal type inside marklogic
    	String query = "SELECT * FROM <acts/metadata> WHERE { ?s ?p \"" + obj + "\" }";
      	
    	// bind obj to 'o' in the query
    	SPARQLQueryDefinition sparqlQuery = sparqlQueryManager.newQueryDefinition(query).withBinding("o", obj);
    	
    	// Initialize Jackson results handle
    	JacksonHandle resultsHandle = new JacksonHandle();
    	resultsHandle.setMimetype(SPARQLMimeTypes.SPARQL_JSON);
    	
    	// execute query
    	resultsHandle = sparqlQueryManager.executeSelect(sparqlQuery, resultsHandle);

    	// parse query result to list of actIds
    	ArrayList<String> actURIs = handleResults(resultsHandle);
    	
    	// find all Acts that fit by ids
    	ArrayList<String> acts = new ArrayList<String>();
    	for(String uri : actURIs) {
    		String[] parts = uri.split("/");
    		String id = parts[2];
    		Act act = findById(client, id);
    		acts.add(toXML(act));
    	}
    	
    	return acts;
    	
    }
    
    // convert resulting triplet to array of actId's (subjects in rdf triplet)
    private static ArrayList<String> handleResults(JacksonHandle resultsHandle) {
		JsonNode tuples = resultsHandle.get().path("results").path("bindings");
		
		ArrayList<String> actIds = new ArrayList<String>();
		for ( JsonNode row : tuples ) {
			String subject = row.path("s").path("value").asText();
			actIds.add(subject);
		}
		return actIds;
	}
    
    // find an act in the database by its id
    public Act findById(DatabaseClient client, String id) throws JAXBException {
    	
    	XMLDocumentManager docMgr = client.newXMLDocumentManager();
    	
    	JAXBContext context = JAXBContext.newInstance(Act.class);
    	
    	JAXBHandle<Act> handle = new JAXBHandle<>(context);
    	
    	docMgr.read("/acts/" + id, handle);
        Act act = handle.get();

		client.release();
		
		return act;
    }

}
