package app.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import app.dto.LoginDTO;
import app.dto.UserDTO;
import app.service.UserService;

@RestController
@RequestMapping(value = "api/user")
public class  UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
    	
    	Map<String, Object> result = userService.login(loginDTO);
    	if(result != null)
    		return ResponseEntity.status(HttpStatus.OK).body(result);
    	else
    		return ResponseEntity.status(HttpStatus.OK).body(result);
        
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.OK).body("Registered successfully");
    }
    
    @RequestMapping(
            value = "/findByText",
            method = RequestMethod.POST,
            consumes = "text/plain"
    )
    public ResponseEntity<?> findByText(@RequestBody String text) throws JAXBException {
    	ArrayList<String> acts = userService.findByText(text);
        return ResponseEntity.status(HttpStatus.OK).body(acts);
    }
    
    @RequestMapping(
            value = "/findByMetadata",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    // method receives predicate and object for the search, exmpl (state="SCHELDUED")
    public ResponseEntity<?> findByMetadata(@RequestParam("pred") String pred, @RequestParam("obj") String obj) throws JAXBException {
    	ArrayList<String> acts = userService.findByTerm(pred, obj);
    	return ResponseEntity.status(HttpStatus.OK).body(acts);
    }
    
    @RequestMapping(
            value = "/exportActsXHTML",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> exportXHTML() throws JAXBException, UnsupportedEncodingException, TransformerException  {
    	ArrayList<String> xhtml = userService.exportActsXHTML();
    	return ResponseEntity.status(HttpStatus.OK).body(xhtml);
    }
    
    @RequestMapping(
            value = "/exportAmendmentsXHTML",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> exportAmendmentsXHTML() throws JAXBException, UnsupportedEncodingException, TransformerException  {
    	ArrayList<String> xhtml = userService.exportAmendmentsXHTML();
    	return ResponseEntity.status(HttpStatus.OK).body(xhtml);
    }
    
    @RequestMapping(
            value = "/exportActsPDF",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> exportActsPDF() throws JAXBException, TransformerException, IOException, DocumentException  {
    	userService.exportActsPDF();
    	return ResponseEntity.status(HttpStatus.OK).body("Generated PDF");
    }
    
    @RequestMapping(
            value = "/exportAmendmentsPDF",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> exportAmendmentsPDF() throws JAXBException, TransformerException, IOException, DocumentException  {
    	userService.exportAmendmentsPDF();
    	return ResponseEntity.status(HttpStatus.OK).body("Generated PDF");
    }
    
    @RequestMapping(
            value = "/exportActMetadataJSON",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public ResponseEntity<?> exportActMetadataJSON() throws JAXBException, TransformerException, IOException, DocumentException  {
    	String metadata = userService.exportActMetadataJSON();
    	return ResponseEntity.status(HttpStatus.OK).body(metadata);
    }
    
    @RequestMapping(
            value = "/exportAmendmentMetadataJSON",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public ResponseEntity<?> exportAmendmentMetadataJSON() throws JAXBException, TransformerException, IOException, DocumentException  {
    	String metadata = userService.exportAmendmentMetadataJSON();
    	return ResponseEntity.status(HttpStatus.OK).body(metadata);
    }

}
