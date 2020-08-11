package ru.ag.TimeTracker.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TimeTrack {

    @JsonView(Views.Together.class)
    private Long taskId;

    @JsonView(Views.QuestionOne.class)
    private Long time;

    @JsonView(Views.QuestionTwo.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy HH:mm")
    private Date dateStart;

    @JsonView(Views.QuestionTwo.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy HH:mm")
    private Date dateEnd;

    @JsonView(Views.QuestionThree.class)
    private Long allTime;

    public TimeTrack(Long id, Date dateStart, Date dateEnd) {
        this.taskId = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public TimeTrack(Long id, Long time) {
        this.taskId = id;
        this.time = time;
    }


    public void plusAllTime(Long time) {
        this.allTime += time;
    }
}
