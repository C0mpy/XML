package app.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import app.dto.RequestDTO;
import app.model.Session;
import app.service.AldermanService;

@RestController
@RequestMapping(value = "api/alderman")
public class AldermanController {
	
	@Autowired
    private AldermanService aldermanService;
	
	@RequestMapping(
            value = "/session/getall",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getSessions() {
		
		ArrayList<Session> sessions = aldermanService.getAllSessions();
		return ResponseEntity.status(HttpStatus.OK).body(sessions);
        
    }
	
	@RequestMapping(
            value = "/session/get/{sessionId}",
            method = RequestMethod.POST,
            produces = "application/json"
    )
	public ResponseEntity<?> getSession(@PathVariable String sessionId) {
		Session session = aldermanService.getSession(sessionId);
		return ResponseEntity.status(HttpStatus.OK).body(session);
		
	}
	
	@RequestMapping(
            value = "/session/{sessionId}/addAct",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML
    )
	public ResponseEntity<?> addAct(@PathVariable String sessionId, @RequestBody String data) throws ParserConfigurationException, SAXException, IOException, JAXBException, TransformerException {
		String actId = aldermanService.addAct(sessionId, data);
		return ResponseEntity.status(HttpStatus.OK).body(actId);
	}
	
	@RequestMapping(
            value = "/act/{actId}/withdraw",
            method = RequestMethod.POST
    )
	public ResponseEntity<?> withdrawAct(@PathVariable String actId) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		aldermanService.withdrawAct(actId);
		return ResponseEntity.status(HttpStatus.OK).body("Act withdrew");
	}
	
	@RequestMapping(
            value = "/act/{actId}/addAmendment",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML
    )
	public ResponseEntity<?> addAmendment(@PathVariable String actId, @RequestBody String data) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		String amendmentId = aldermanService.addAmendment(actId, data);
		return ResponseEntity.status(HttpStatus.OK).body(amendmentId);
	}
	
	@RequestMapping(
            value = "/amendment/{amendmentId}/withdraw",
            method = RequestMethod.POST
    )
	public ResponseEntity<?> withdrawAmendment(@PathVariable String amendmentId) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		aldermanService.withdrawAmendment(amendmentId);
		return ResponseEntity.status(HttpStatus.OK).body("Amendment withdrew");
	}

}
