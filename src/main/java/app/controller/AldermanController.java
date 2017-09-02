package app.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import app.dto.ActDTO;
import app.dto.RequestDTO;
import app.model.Session;
import app.service.AldermanService;

@RestController
@RequestMapping(value = "api/alderman")
public class AldermanController {
	
	@Autowired
    private AldermanService aldermanService;
	
	@RequestMapping(
            value = "/getSessions",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getSessions() {
		
		ArrayList<Session> sessions = aldermanService.getAllSessions();
		return ResponseEntity.status(HttpStatus.OK).body(sessions);
        
    }
	
	@RequestMapping(
            value = "/getSession",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
	public ResponseEntity getSession(@RequestBody RequestDTO requestDTO) {
		Session session = aldermanService.getSession(requestDTO.getRequest());
		return ResponseEntity.status(HttpStatus.OK).body(session);
		
	}
	
	@RequestMapping(
            value = "/session/{sessionId}/addAct",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML
    )
	public void addAct(@PathVariable String sessionId, @RequestBody String data) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		aldermanService.addAct(sessionId, data);
	}
	
	@RequestMapping(
            value = "/act/{actId}/addAmendment",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML
    )
	public void addAmendment(@PathVariable String actId, @RequestBody String data) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		System.out.println(actId);
		aldermanService.addAmendment(actId, data);
	}
	

}
