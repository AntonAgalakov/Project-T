package ru.ag.TimeTracker.model;

public final class Views {

    /*
        In order not to always show all information in full, I use Json Views.
        For example, when I want to see a complete list of tasks,
        I am not interested in detailed information about each of them.
     */

    /* For model User */

    public interface IdName {} /* Only displayed Id and Name */

    public interface FullInfo extends IdName {} /* Full info about User */

    /* For model Task */

    public interface IdDescriptionStatus {} /* Only displayed Id, Description and Status */

    public interface FullTask extends IdDescriptionStatus {} /* Full info about Task */

}
