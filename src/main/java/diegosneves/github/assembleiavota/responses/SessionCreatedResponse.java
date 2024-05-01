package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionCreatedResponse {

    private String sessionId;
    private TopicVotedDTO topicVotedDTO;
    private boolean isOpen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
