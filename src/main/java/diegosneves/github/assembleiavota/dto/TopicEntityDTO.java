package diegosneves.github.assembleiavota.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TopicEntityDTO {

    private String topicId;
    private String title;
    private String description;
    private Integer votingSessionDuration;
}
