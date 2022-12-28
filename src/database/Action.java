package database;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Action {
    @Getter @Setter
    private String type;
    @Getter @Setter
    private String page;
    @Getter @Setter
    private String feature;
    @Getter @Setter
    private Credentials credentials;
    @Getter @Setter
    private String startsWith;
    @Getter @Setter
    private Filter filters;
    @Getter @Setter
    private ArrayList<Movie> movies;
    @Getter @Setter
    private String count;
    @Getter @Setter
    private String movie;
    @Getter @Setter
    private double rate;
    @Getter @Setter
    private String subscribedGenre;
    @Getter @Setter
    private Movie addedMovie;
    @Getter @Setter
    private String deletedMovie;

    public Action() {
    }

}
