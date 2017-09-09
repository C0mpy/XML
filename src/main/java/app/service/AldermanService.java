package app.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.ActRepository;
import app.repository.AmendmentRepository;
import app.repository.SessionRepository;
import app.jaxb_model.Act;
import app.jaxb_model.Amendment;
import app.model.Session;
import app.jaxb_model.Status;

@Service
public class AldermanService {
	
	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private ActRepository actRepository;
	
	@Autowired
	private AmendmentRepository amendmentRepository;
	
	public ArrayList<Session> getAllSessions() {
		return (ArrayList<Session>) sessionRepository.findAll();
	}

	public Session getSession(String sessionId) {
		return sessionRepository.findOne(Long.parseLong(sessionId));
	}
	
	public String addAct(String sessionId, String xmlAct) throws JAXBException, UnsupportedEncodingException, TransformerException {

		// set the class we want to unmarshal to
		JAXBContext context = JAXBContext.newInstance(Act.class);
		
		// create unmarshaller we use for converting xml to objects
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// convert xml string to a strem we can later use it for unmarshal operation
		InputStream stream = new ByteArrayInputStream(xmlAct.getBytes(StandardCharsets.UTF_8));
		
		// unmarshall
		Act act = (Act)unmarshaller.unmarshal(stream);
		
		// generate ID for Act and all its child elements
		act.generateId();
		act.setSessionId(sessionId);
		act.setStatus(Status.SCHEDULED);
		
		// save the act in the marklogic database
		actRepository.save(act, sessionId);
		
		// bind the new act to its session in the mysql database
		Session session = sessionRepository.findOne(Long.parseLong(sessionId));
		session.getActs().add(act.getId());
		
		sessionRepository.save(session);
		
		return act.getId();
		
	}
	
	public void withdrawAct(String actId) throws JAXBException {
		actRepository.setStatus(actId, "WITHDREW");
	}
	
	public String addAmendment(String actId, String xmlAmendment) throws JAXBException, UnsupportedEncodingException, TransformerException {

		// set the class we want to unmarshal to
		JAXBContext context = JAXBContext.newInstance(Amendment.class);
		
		// create unmarshaller we use for converting xml to objects
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// convert xml string to a strem we can later use it for unmarshal operation
		InputStream stream = new ByteArrayInputStream(xmlAmendment.getBytes(StandardCharsets.UTF_8));
		
		// unmarshall
		Amendment amendment = (Amendment)unmarshaller.unmarshal(stream);
		amendment.setId(UUID.randomUUID().toString());
		amendment.setActId(actId);
		amendment.setStatus(Status.SCHEDULED);
		
		amendmentRepository.save(amendment);
		return amendment.getId();
		
	}
	
	public void withdrawAmendment(String amendmentId) throws JAXBException {
		amendmentRepository.setStatus(amendmentId, "WITHDREW");
	}

}
