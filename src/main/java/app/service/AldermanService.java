package app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.repository.SessionRepository;
import app.model.Session;

@Service
public class AldermanService {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	public ArrayList<Session> getAllSessions() {
		return (ArrayList<Session>) sessionRepository.findAll();
	}

	public Session getSession(String sessionId) {
		System.out.println(sessionId);
		return sessionRepository.findOne(Long.parseLong(sessionId));
	}

}
