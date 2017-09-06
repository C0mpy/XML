package app.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.jaxb_model.Amendment;
import app.jaxb_model.Target;
import app.model.Session;
import app.repository.ActRepository;
import app.repository.AmendmentRepository;
import app.repository.SessionRepository;

@Service
public class PresidentService {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private ActRepository actRepository;
	
	@Autowired
	private AmendmentRepository amendmentRepository;
	
	public void addSession(Session s) {
		sessionRepository.save(s);
	}
	
	public void startSession(String sessionId) {
		Session session = sessionRepository.findOne(Long.parseLong(sessionId));
		session.setState("STARTED");
		sessionRepository.save(session);
	}
	
	public void endSession(String sessionId) {
		Session session = sessionRepository.findOne(Long.parseLong(sessionId));
		session.setState("ENDED");
		sessionRepository.save(session);
	}
	
	public void acceptAct(String actId) {
		actRepository.setStatus(actId, "ACCEPTED");	
	}
	
	public void rejectAct(String actId) {
		actRepository.setStatus(actId, "REJECTED");	
	}
	
	public void acceptAmendment(String amendmentId) throws JAXBException {
		
		// set status of the amendment in database
		amendmentRepository.setStatus(amendmentId, "ACCEPTED");
		Amendment amendment = amendmentRepository.findOne(amendmentId);
		
		for(Target t : amendment.getTarget()) {
			actRepository.update(amendment.getActId(), t);
		}
	}
	
	public void rejectAmendment(String amendmentId) {
		amendmentRepository.setStatus(amendmentId, "REJECTED");
	}

}
