package diegosneves.github.assembleiavota.controllers;

import diegosneves.github.assembleiavota.controllers.contracts.TopicControllerContract;
import diegosneves.github.assembleiavota.requests.TopicRequest;
import diegosneves.github.assembleiavota.responses.TopicCreatedResponse;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/topic")
public class TopicController implements TopicControllerContract {

    private final TopicServiceContract topicService;

    @Autowired
    public TopicController(TopicServiceContract topicService) {
        this.topicService = topicService;
    }

    @Override
    public ResponseEntity<TopicCreatedResponse> createTopic(TopicRequest request) {
        TopicCreatedResponse topicResponse = this.topicService.createNewTopic(request);
        return ResponseEntity.ok(topicResponse);
    }
}
