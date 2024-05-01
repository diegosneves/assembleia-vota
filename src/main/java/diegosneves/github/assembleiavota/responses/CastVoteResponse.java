package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.enums.UserStatus;
import diegosneves.github.assembleiavota.enums.VoteOption;
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
public class CastVoteResponse {

    private String voterCpf;
    private UserStatus status;
    private String sessionId;
    private Integer totalVoters;
    private TopicVotedDTO topicVoted;
    private VoteOption chosenVoteOption;
    private boolean voteTaken = false;

}
