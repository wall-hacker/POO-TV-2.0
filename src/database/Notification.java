package database;

import lombok.Getter;
import lombok.Setter;

public class Notification {
    @Getter @Setter
    private String movieName;
    @Getter @Setter
    private String message;

    public Notification(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }
}
