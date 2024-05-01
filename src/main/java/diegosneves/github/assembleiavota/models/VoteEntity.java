package diegosneves.github.assembleiavota.models;

import diegosneves.github.assembleiavota.enums.VoteOption;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity(name = "votes")
@Table(name = "votes", uniqueConstraints= @UniqueConstraint(columnNames={"voterCpf", "topicId"}))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VoteEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String voteId;
    @Column(name = "voterCpf", nullable = false)
    private String voterCpf;
    @ManyToOne
    private SessionEntity session;
    @Column(name = "topicId", nullable = false)
    private String topicId;
    private LocalDateTime votedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "vote", nullable = false)
    private VoteOption vote;

}
