package ru.ag.TimeTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdDescriptionStatus.class)
    private Long id;

    @JsonView(Views.FullTask.class)
    private Long userId;

    @JsonView(Views.IdDescriptionStatus.class)
    private String description;

    @JsonView(Views.IdDescriptionStatus.class)
    private String status;

    @JsonView(Views.FullTask.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dateStart;

    @JsonView(Views.FullTask.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dateEnd;

}
