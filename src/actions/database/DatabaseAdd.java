package actions.database;

import actions.entities.FormattedOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Notification;
import database.Action;
import database.Input;
import database.Movie;
import database.User;

import java.util.ArrayList;

public final class DatabaseAdd extends DatabaseAction {
    @Override
    public void action(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) {
        int found = 0;
        Movie addedMovie = action.getAddedMovie();
        String movieName = addedMovie.getName();
        ArrayList<Movie> movies = input.getMovies();

        for (Movie movie : movies) {
            if (movie.getName().equals(movieName) && found == 0) {
                found = 1;
            }
        }

        if (found == 1) {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        } else {
            input.getMovies().add(addedMovie);
        }
    }

    @Override
    public void notify(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) {
        Movie movie = action.getAddedMovie();
        int sent = 0;

        for (User user : input.getUsers()) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                if (user.getSubcribedGenres() != null) {
                    for (String userGenre : user.getSubcribedGenres()) {
                        if (movie.getGenres().contains(userGenre) && sent == 0) {
                            Notification newNotification = new Notification(movie.getName(), "ADD");
                            user.getNotifications().add(newNotification);
                            sent = 1;
                        }
                    }
                }
            }
        }
    }
}

