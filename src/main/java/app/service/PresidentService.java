package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public void acceptAmendment(String amendmentId) {
		amendmentRepository.setStatus(amendmentId, "ACCEPTED");	
	}
	
	public void rejectAmendment(String amendmentId) {
		amendmentRepository.setStatus(amendmentId, "REJECTED");
	}

}
