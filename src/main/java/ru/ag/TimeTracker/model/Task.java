package ru.ag.TimeTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString(of = {"id", "description"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdDescriptionStatus.class)
    private Long id;
    @JsonView(Views.IdDescriptionStatus.class)
    private String description;
    @JsonView(Views.IdDescriptionStatus.class)
    private String status;

    @Column(name = "data_start", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") // change format date
    @JsonView(Views.FullTask.class)
    private Date dateStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") // change format date
    @JsonView(Views.FullTask.class)
    @Column(name = "data_end")
    private Date dateEnd;
}
