package ru.ag.TimeTracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@JsonView(Views.IdDescriptionStatus.class)
    private Long id;
    //@JsonView(Views.IdDescriptionStatus.class)
    private String description;

    private String status;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    @ManyToOne
    @JsonIgnore
    private User userId;

}
