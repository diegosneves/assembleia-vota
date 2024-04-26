package diegosneves.github.assembleiavota.requests;

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
public class TopicRequest {

    private String title;
    private String description;
    private Integer votingSessionDuration;

}
