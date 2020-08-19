package ru.ag.TimeTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdTitleAreaStatus.class)
    private Long id;

    @JsonView(Views.FullTask.class)
    private Long userId;

    @JsonView(Views.IdTitleAreaStatus.class)
    @Column(length = 40)
    private String title;

    @JsonView(Views.IdTitleAreaStatus.class)
    @Column(length = 25)
    private String area;

    @JsonView(Views.FullTask.class)
    private String description;

    @JsonView(Views.IdTitleAreaStatus.class)
    private String status;

    @JsonView(Views.FullTask.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dateStart;

    @JsonView(Views.FullTask.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date dateEnd;

    /* For test*/
    public Task(String description) {
        this.description = description;
    }

    public Task(Long taskId, Long userId, String title, String description) {
        this.id = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }
}
