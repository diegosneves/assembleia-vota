package diegosneves.github.assembleiavota.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TopicCreatedResponse {

    private String topicId;
    private String title;
    private String description;
    private Integer votingSessionDuration;

}
