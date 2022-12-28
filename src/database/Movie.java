package database;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class Movie {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String year;
    @Getter @Setter
    private Integer duration;
    @Getter @Setter
    private ArrayList<String> genres;
    @Getter @Setter
    private ArrayList<String> actors;
    @Getter @Setter
    private ArrayList<String> countriesBanned;
    @Getter @Setter
    private int numLikes;
    @Getter @Setter
    private double rating = 0;
    @Getter @Setter
    private int numRatings;

    public Movie() {
    }

    public Movie(final Movie movie) {
        this.name = movie.name;
        this.year = movie.year;
        this.duration = movie.duration;
        this.genres = new ArrayList();
        this.genres.addAll(movie.genres);
        this.actors = new ArrayList();
        this.actors.addAll(movie.actors);
        this.countriesBanned = new ArrayList();
        this.countriesBanned.addAll(movie.countriesBanned);
        this.numLikes = movie.numLikes;
        this.rating = movie.rating;
        this.numRatings = movie.numRatings;
    }

    /**
     * method that increments the number of likes
     */
    public void incrementNumLikes() {
        this.numLikes++;
    }
    /**
     * method that increments the number of ratings
     */
    public void incrementNumRatings() {
        this.numRatings++;
    }

}
