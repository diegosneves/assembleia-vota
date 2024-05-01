package diegosneves.github.assembleiavota.requests;

import diegosneves.github.assembleiavota.enums.VoteOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CastVoteRequest {

    private String userCpf;
    private String sessionId;
    private VoteOption chosenVoteOption;

}
