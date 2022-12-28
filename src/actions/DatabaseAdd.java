package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.*;

import java.util.ArrayList;

public class DatabaseAdd extends DatabaseAction {
    @Override
    public void action(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) {
        Movie addedMovie = action.getAddedMovie();
        String movieName = addedMovie.getName();

        int found = 0;
        ArrayList<Movie> movies = input.getMovies();

        for(Movie movie : movies) {
            if(movie.getName().equals(movieName) && found == 0)
                found = 1;
        }

        if(found == 1) {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        } else {
            input.getMovies().add(addedMovie);
        }
    }

    @Override
    public void notify(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) {
        Movie movie = action.getAddedMovie();

        for(User user : input.getUsers()) {
            if(movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                //output.add(objectMapper.valueToTree(new FormattedOutput()));
            } else {
                if (user.getSubcribedGenres() != null) {
                    for (String userGenre : user.getSubcribedGenres()) {
                        if (movie.getGenres().contains(userGenre)) {
                            Notification newNotification = new Notification(movie.getName(), "ADD");
                            user.getNotifications().add(newNotification);
                        }
                    }
                }
            }
        }
    }
}

