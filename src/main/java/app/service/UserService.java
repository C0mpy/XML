package app.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import app.dto.LoginDTO;
import app.jaxb_model.Act;
import app.model.Alderman;
import app.model.President;
import app.model.User;
import app.repository.ActRepository;
import app.repository.AmendmentRepository;
import app.repository.UserRepository;
import app.util.PDFExtractor;
import app.util.XHTMLExtractor;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private ActRepository actRepository;
    
    @Autowired
    private AmendmentRepository amendmentRepository;

    public Map<String, Object> login(LoginDTO loginDTO) {  	
    	
    	User user = repository.findOneByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
    	Map<String, Object> data = new HashMap<String, Object>();
    	if(user != null && user.getPassword().equals(loginDTO.getPassword())) {
    		data.put("user", user);
    		if(user instanceof Alderman)
				data.put("type", "Alderman");
			else if(user instanceof President)
				data.put("type", "President");
    		return data;
    	}
    	else {
    		return null;
    	}
        
    }
    
    public ArrayList<String> findByText(String text) throws JAXBException {
    	return actRepository.findByText(text);
    }
    
    public ArrayList<String> findByTerm(String pred, String obj) throws JAXBException {
    	return actRepository.findByTerm(pred, obj);
    }
    
    public ArrayList<String> exportActsXHTML() throws JAXBException, UnsupportedEncodingException, TransformerException {
    	ArrayList<String> acts = actRepository.findAll();
    	ArrayList<String> xhtml = new ArrayList<String>();
    	for(String act : acts) {
    		xhtml.add(XHTMLExtractor.extract(act, "src/main/resources/xsl/act_to_xhtml.xsl"));
    	}
    	return xhtml;
    }
    
    public ArrayList<String> exportAmendmentsXHTML() throws JAXBException, UnsupportedEncodingException, TransformerException {
    	ArrayList<String> amendments = amendmentRepository.findAll();
    	ArrayList<String> xhtml = new ArrayList<String>();
    	System.out.println(amendments.size());
    	for(String act : amendments) {
    		xhtml.add(XHTMLExtractor.extract(act, "src/main/resources/xsl/amendment_to_xhtml.xsl"));
    	}
    	return xhtml;
    }
    
    public void exportActsPDF() throws JAXBException, TransformerException, IOException, DocumentException {
    	ArrayList<String> acts = actRepository.findAll();
    	for(int i = 0; i < acts.size(); i++) {
    		String xhtml = XHTMLExtractor.extract(acts.get(i), "src/main/resources/xsl/act_to_xhtml.xsl");
    		PDFExtractor.extract(xhtml, "gen/act" + i + ".pdf");
    	}
    }
    
    public void exportAmendmentsPDF() throws JAXBException, TransformerException, IOException, DocumentException {
    	ArrayList<String> amendments = amendmentRepository.findAll();
    	for(int i = 0; i < amendments.size(); i++) {
    		String xhtml = XHTMLExtractor.extract(amendments.get(i), "src/main/resources/xsl/amendment_to_xhtml.xsl");
    		PDFExtractor.extract(xhtml, "gen/amendment" + i + ".pdf");
    	}
    }
    
}
