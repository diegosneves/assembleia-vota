package diegosneves.github.assembleiavota.controllers;

import diegosneves.github.assembleiavota.controllers.contracts.SessionControllerContract;
import diegosneves.github.assembleiavota.requests.StartSessionRequest;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;
import diegosneves.github.assembleiavota.services.contract.SessionServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/session")
public class SessionController implements SessionControllerContract {

    private final SessionServiceContract sessionService;

    @Autowired
    public SessionController(SessionServiceContract sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public ResponseEntity<SessionCreatedResponse> startSession(StartSessionRequest request) {
        SessionCreatedResponse response = this.sessionService.startSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
