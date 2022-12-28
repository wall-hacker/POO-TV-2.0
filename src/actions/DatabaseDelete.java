package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.*;

import java.util.ArrayList;

public class DatabaseDelete extends DatabaseAction {
    @Override
    public void action(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) {
        String movieName = action.getDeletedMovie();
        ArrayList<Movie> movies = input.getMovies();
        Movie foundMovie = null;

        for(Movie movie : movies) {
            if(movie.getName().equals(movieName)) {
                foundMovie = movie;
            }
        }

        if(foundMovie != null) {
            movies.remove(foundMovie);
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    @Override
    public void notify(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) {
        String movieName = action.getDeletedMovie();
        ArrayList<Movie> movies = input.getMovies();
        Movie foundMovie = null;

        for(Movie movie : movies) {
            if (movie.getName().equals(movieName)) {
                foundMovie = movie;
            }
        }
        if(foundMovie != null) {
            for(User user : input.getUsers()) {
                if (user.getPurchasedMovies().contains(foundMovie)) {
                    Notification newNotification = new Notification(movieName, "DELETE");
                    user.getNotifications().add(newNotification);

                    if(user.getCredentials().getAccountType().equals("premium")) {
                        user.incrementNumFreePremiumMovies();
                    } else {
                        user.incrementTokensCount(2);
                    }
                }
            }
        }
    }
}
