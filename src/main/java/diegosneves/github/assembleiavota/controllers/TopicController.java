package diegosneves.github.assembleiavota.controllers;

import diegosneves.github.assembleiavota.controllers.contracts.TopicControllerContract;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/topic")
public class TopicController implements TopicControllerContract {

    @Override
    public ResponseEntity<TopicCreatedResponse> createTopic(TopicRequest request) {
        return null;
    }
}
