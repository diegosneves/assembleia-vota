package diegosneves.github.assembleiavota.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum VoteOption {
    @JsonProperty(value = "Sim")
    YES,
    @JsonProperty(value = "Não")
    NO;
}
