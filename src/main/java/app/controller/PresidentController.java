package app.controller;

import app.util.DateFormat;
import app.dto.RequestDTO;
import app.model.Session;
import app.service.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
            produces = "application/text"
    )
    public ResponseEntity<?> addSession(@RequestBody RequestDTO requestDTO) {
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
            value = "/session/{sessionId}/start",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> startSession(@PathVariable String sessionId) {
		presidentService.startSession(sessionId);
		return ResponseEntity.status(HttpStatus.OK).body("Session started");
    }
	
	@RequestMapping(
            value = "/session/{sessionId}/end",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> endSession(@PathVariable String sessionId) {
		presidentService.endSession(sessionId);
		return ResponseEntity.status(HttpStatus.OK).body("Session ended");
    }
	
	@RequestMapping(
            value = "/act/{actId}/accept",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> acceptAct(@PathVariable String actId) {
		presidentService.acceptAct(actId);
		return ResponseEntity.status(HttpStatus.OK).body("Act accepted");
    }
	
	@RequestMapping(
            value = "/act/{actId}/reject",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> rejectAct(@PathVariable String actId) {
		presidentService.rejectAct(actId);
		return ResponseEntity.status(HttpStatus.OK).body("Act rejected");
    }
	
	@RequestMapping(
            value = "/amendment/{amendmentId}/accept",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> acceptAmendment(@PathVariable String amendmentId) {
		presidentService.acceptAmendment(amendmentId);
		return ResponseEntity.status(HttpStatus.OK).body("Amendment accepted");
    }
	
	@RequestMapping(
            value = "/amendment/{amendmentId}/reject",
            method = RequestMethod.POST
    )
    public ResponseEntity<?> rejectAmendment(@PathVariable String amendmentId) {
		presidentService.rejectAmendment(amendmentId);
		return ResponseEntity.status(HttpStatus.OK).body("Amendment rejected");
    }

}