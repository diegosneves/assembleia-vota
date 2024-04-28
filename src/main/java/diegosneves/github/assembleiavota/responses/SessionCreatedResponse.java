package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicEntityDTO;
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
    private TopicEntityDTO topicEntityDTO;
    private boolean isOpen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


}
