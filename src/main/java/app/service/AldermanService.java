package app.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import app.repository.ActRepository;
import app.repository.SessionRepository;
import app.jaxb_model.Act;
import app.model.Session;

@Service
public class AldermanService {
	
	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private ActRepository actRepository;
	
	public ArrayList<Session> getAllSessions() {
		return (ArrayList<Session>) sessionRepository.findAll();
	}

	public Session getSession(String sessionId) {
		return sessionRepository.findOne(Long.parseLong(sessionId));
	}
	
	public void addAct(String xmlAct) throws ParserConfigurationException, SAXException, IOException, JAXBException {

		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("app.jaxb_model");
		
		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		// konvertuje se sadrzaj iz Stringa u InputStream kako bi mogli Unmarshall
		InputStream stream = new ByteArrayInputStream(xmlAct.getBytes(StandardCharsets.UTF_8));
		
		// unmarshall XMLStringa u Act objekat
		Act act = (Act)unmarshaller.unmarshal(stream);
		act.setId(UUID.randomUUID().toString());
		System.out.println(act);
		
		// call repository's save method
		actRepository.save(act);
		
	}

}
