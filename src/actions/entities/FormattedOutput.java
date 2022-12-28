package actions.entities;

import database.Movie;
import database.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FormattedOutput {
    @Getter @Setter
    private String error;
    @Getter @Setter
    private ArrayList<Movie> currentMoviesList;
    @Getter @Setter
    private User currentUser;

    public FormattedOutput() {
        this.error = "Error";
        this.currentMoviesList = new ArrayList<Movie>();
        this.currentUser = null;
    }

    public FormattedOutput(final List<Movie> currentMoviesList, final User currentUser) {
        this.currentMoviesList = new ArrayList<Movie>();
        for (Movie movie : currentMoviesList) {
            this.currentMoviesList.add(new Movie(movie));
        }
        this.currentUser = new User(currentUser);
    }

    public FormattedOutput(final User currentUser, final Movie movie) {
        this.currentUser = new User(currentUser);
        this.currentMoviesList = new ArrayList<Movie>();
        this.currentMoviesList.add(new Movie(movie));
    }

    public FormattedOutput(final User currentUser) {
        this.currentUser = new User(currentUser);
        this.currentMoviesList = null;
        this.error = null;
    }
}
