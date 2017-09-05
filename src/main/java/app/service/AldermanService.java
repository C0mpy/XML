package app.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.ActRepository;
import app.repository.AmendmentRepository;
import app.repository.SessionRepository;
import app.jaxb_model.Act;
import app.jaxb_model.Amendment;
import app.jaxb_model.Status;
import app.model.Session;

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
	
	public void addAct(String sessionId, String xmlAct) throws JAXBException {

		// set the class we want to unmarshal to
		JAXBContext context = JAXBContext.newInstance(Act.class);
		
		// create unmarshaller we use for converting xml to objects
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// convert xml string to a strem we can later use for unmarshal operation
		InputStream stream = new ByteArrayInputStream(xmlAct.getBytes(StandardCharsets.UTF_8));
		
		// unmarshall
		Act act = (Act)unmarshaller.unmarshal(stream);
		
		// generate ID for Act and all its child elements
		act.generateId();
		
		actRepository.save(act, sessionId);
		
	}
	
	public void withdrawAct(String actId) throws JAXBException {
		actRepository.setStatus(actId, "WITHDREW");
	}
	
	public void addAmendment(String actId, String xmlAmendment) throws JAXBException {

		// set the class we want to unmarshal to
		JAXBContext context = JAXBContext.newInstance(Amendment.class);
		
		// create unmarshaller we use for converting xml to objects
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// konvertuje se sadrzaj iz Stringa u InputStream kako bi mogli Unmarshall
		InputStream stream = new ByteArrayInputStream(xmlAmendment.getBytes(StandardCharsets.UTF_8));
		
		// unmarshall XMLStringa u Amendment objekat
		Amendment amendment = (Amendment)unmarshaller.unmarshal(stream);
		amendment.setId(UUID.randomUUID().toString());
		amendment.setActId(actId);
		amendment.setStatus(Status.SCHEDULED);
		
		// call repository's save method
		amendmentRepository.save(amendment);
		
	}
	
	public void withdrawAmendment(String amendmentId) throws JAXBException {
		amendmentRepository.setStatus(amendmentId, "WITHDREW");
	}

}
