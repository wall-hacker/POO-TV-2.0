package actions.changepage;

import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import actions.entities.FormattedOutput;
import actions.back.Back;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import database.Movie;

import java.util.ArrayList;

abstract class ChangePageAction {
    static void login(final CurrentPage currentPage, final CurrentUser currentUser,
                      final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
            currentPage.setCurrentPage("login", null, null);
            Back.getPageHistory().add("login");
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void register(final CurrentPage currentPage, final CurrentUser currentUser,
                         final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() == null && currentPage.getName().equals("homepage")) {
            currentPage.setCurrentPage("register", null, null);
            Back.getPageHistory().add("register");
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void logout(final CurrentPage currentPage, final CurrentUser currentUser,
                       final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() != null) {
            currentUser.setUser(null);
            currentPage.setCurrentPage("homepage", null, null);
            Back.getPageHistory().clear();
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void movies(final CurrentPage currentPage, final CurrentUser currentUser,
                       final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() != null) {
            currentPage.setMoviesList(currentPage.getInput().getMovies());

            ArrayList<Movie> movies = currentPage.getMoviesList();
            ArrayList<Movie> unbannedMovies = new ArrayList<Movie>();
            for (Movie movie : movies) {
                String country = currentUser.getUser().getCredentials().getCountry();
                if (!movie.getCountriesBanned().contains(country)) {
                    unbannedMovies.add(movie);
                }
            }
            currentPage.setCurrentPage("movies", null, unbannedMovies);

            output.add(objectMapper.valueToTree(new FormattedOutput(currentPage.getMoviesList(),
                    currentUser.getUser())));

            Back.getPageHistory().add("movies");
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void seeDetails(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("movies")) {
            Movie currentMovie = null;
            ArrayList<Movie> movies = currentPage.getMoviesList();
            for (Movie movie : movies) {
                if (movie.getName().equals(action.getMovie())) {
                    currentMovie = movie;
                }
            }
            if (currentMovie != null) {
                currentPage.setCurrentPage("see details", currentMovie, movies);
                output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(),
                        currentMovie)));
                Back.getPageHistory().add("see details");
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void upgrades(final CurrentPage currentPage, final CurrentUser currentUser,
                         final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() != null) {
            currentPage.setCurrentPage("upgrades", null, null);
            Back.getPageHistory().add("upgrades");
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }
}
