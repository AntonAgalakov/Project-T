package ru.ag.TimeTracker.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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


    public void plusAllTime(Long time) {
        this.allTime += time;
    }
}
