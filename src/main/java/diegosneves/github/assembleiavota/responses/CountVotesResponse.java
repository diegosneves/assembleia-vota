package diegosneves.github.assembleiavota.responses;

import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
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
public class CountVotesResponse {

    private TopicVotedDTO topicVoted;
    private int votesSum;
    private int totalVotesInFavor;
    private int totalVotesAgainst;

}
