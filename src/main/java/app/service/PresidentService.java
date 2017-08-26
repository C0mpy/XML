package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Session;
import app.repository.SessionRepository;
import app.repository.UserRepository;

@Service
public class PresidentService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	public void addSession(Session s) {
		sessionRepository.save(s);
	}
	
	public void startSession(String sessionId) {
		Session session = sessionRepository.findOne(Long.parseLong(sessionId));
		session.setState("STARTED");
		sessionRepository.save(session);
	}
	

}
