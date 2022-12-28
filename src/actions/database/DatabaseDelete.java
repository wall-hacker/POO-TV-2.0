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

public final class DatabaseDelete extends DatabaseAction {
    @Override
    public void action(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) {
        String movieName = action.getDeletedMovie();
        ArrayList<Movie> movies = input.getMovies();
        Movie foundMovie = null;

        for (Movie movie : movies) {
            if (movie.getName().equals(movieName)) {
                foundMovie = movie;
            }
        }

        if (foundMovie != null) {
            movies.remove(foundMovie);
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    @Override
    public void notify(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) {
        String movieName = action.getDeletedMovie();
        ArrayList<Movie> movies = input.getMovies();
        Movie foundMovie = null;

        for (Movie movie : movies) {
            if (movie.getName().equals(movieName)) {
                foundMovie = movie;
            }
        }
        if (foundMovie != null) {
            for (User user : input.getUsers()) {
                if (user.getPurchasedMovies().contains(foundMovie)) {
                    Notification newNotification = new Notification(movieName, "DELETE");
                    user.getNotifications().add(newNotification);

                    if (user.getCredentials().getAccountType().equals("premium")) {
                        user.incrementNumFreePremiumMovies();
                    } else {
                        user.incrementTokensCount(2);
                    }
                }
            }
        }
    }
}
