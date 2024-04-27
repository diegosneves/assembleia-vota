package diegosneves.github.assembleiavota.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "sessions")
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SessionEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String sessionId;
    @ManyToOne
    private TopicEntity topic;
    private boolean isOpen;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    @OneToMany(mappedBy = "sessions")
//    private List<Vote> votes;

}
