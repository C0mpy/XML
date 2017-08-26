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
			return ResponseEntity.status(HttpStatus.OK).body("Session scheldued");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Parameter");
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
		return ResponseEntity.status(HttpStatus.OK).body("Session started");
    }
	
	@RequestMapping(
            value = "/endSession",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity endSession(@RequestBody RequestDTO requestDTO) {
		presidentService.endSession(requestDTO.getRequest());
		return ResponseEntity.status(HttpStatus.OK).body("Session ended");
    }

}