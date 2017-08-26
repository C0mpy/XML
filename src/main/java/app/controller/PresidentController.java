package app.controller;

import app.util.DateFormat;
import app.dto.RequestDTO;
import app.model.Session;
import app.service.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/president")
public class PresidentController {
	
	@Autowired
    private PresidentService presidentService;

	@RequestMapping(
            value = "/addSession",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity addSession(@RequestBody RequestDTO requestDTO) {
		if(DateFormat.isValid(requestDTO.getRequest())) {
			Session session = new Session(requestDTO.getRequest());
			presidentService.addSession(session);
			return new ResponseEntity("Session scheldued", HttpStatus.OK);
		}
		else {
			return new ResponseEntity("Bad Parameter", HttpStatus.BAD_REQUEST);
		}
        
    }
	
	@RequestMapping(
            value = "/startSession",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity startSession(@RequestBody RequestDTO requestDTO) {
		presidentService.startSession(requestDTO.getRequest());
		return new ResponseEntity("Session started", HttpStatus.OK);
    }

}