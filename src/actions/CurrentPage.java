package actions;

import database.Input;
import database.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class CurrentPage {
    @Setter
    private Input input;
    @Getter @Setter
    private String name;
    @Getter
    private ArrayList<Movie> moviesList;
    @Getter @Setter
    private Movie currentMovie;

    public CurrentPage(final String newName, final Input newInput) {
        this.input = newInput;
        this.name = newName;
    }

    /**
     * input getter
     * @return
     */
    public Input getInput() {
        return input;
    }

    /**
     * Singleton-like setter for the movieList array
     * @param moviesList
     */
    public void setMoviesList(final ArrayList<Movie> moviesList) {
        if (moviesList != null) {
            this.moviesList = new ArrayList(moviesList);
        } else {
            this.moviesList = new ArrayList();
        }
    }

    /**
     * method that sets all the parameters of a current page at once
     * @param newName
     * @param newCurrentMovie
     * @param newMoviesList
     */
    public void setCurrentPage(final String newName, final Movie newCurrentMovie,
                               final ArrayList<Movie> newMoviesList) {
        this.setName(newName);
        this.setCurrentMovie(newCurrentMovie);
        this.setMoviesList(newMoviesList);
    }
}
