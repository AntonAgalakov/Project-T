package ru.ag.TimeTracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;

    @JsonView(Views.IdName.class)
    private String name;

}
