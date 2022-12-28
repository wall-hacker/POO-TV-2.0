package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.*;

import java.util.ArrayList;
import java.util.Comparator;

abstract class OnPageAction {
    static void login(final Action action, final CurrentPage currentPage,
                      final CurrentUser currentUser, final ArrayNode output,
                      final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("login") && currentUser.getUser() == null) {
            User validUser = null;
            Credentials loginCredentials = action.getCredentials();
            ArrayList<User> users = currentPage.getInput().getUsers();
            for (User user : users) {
                if (user.getCredentials().getName().equals(loginCredentials.getName())
                        && user.getCredentials().getPassword().equals(
                                loginCredentials.getPassword())) {
                    validUser = user;
                }
            }
            if (validUser != null) {
                currentUser.setUser(validUser);
                output.add(objectMapper.valueToTree(new
                        FormattedOutput(currentPage.getMoviesList(), currentUser.getUser())));
                Back.getPageHistory().add("hpauth");
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
        currentPage.setName("homepage");
    }

    static void register(final Action action, final CurrentPage currentPage,
                         final CurrentUser currentUser, final ArrayNode output,
                         final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("register") && currentUser.getUser() == null) {
            User validUser = null;
            Credentials registerCredentials = action.getCredentials();
            ArrayList<User> users = currentPage.getInput().getUsers();
            for (User user : users) {
                if (user.getCredentials().getName().equals(registerCredentials.getName())
                        && user.getCredentials().getPassword().equals(
                                registerCredentials.getPassword())) {
                    validUser = user;
                }
            }
            if (validUser == null) {
                validUser = new User(registerCredentials);
                currentPage.getInput().addUser(validUser);
                currentUser.setUser(validUser);
                output.add(objectMapper.valueToTree(new FormattedOutput(currentPage.getMoviesList(), validUser)));
                Back.getPageHistory().add("hpauth");
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
        currentPage.setName("homepage");
    }

    static void search(final Action action, final CurrentPage currentPage,
                       final CurrentUser currentUser, final ArrayNode output,
                       final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("movies")) {
            ArrayList<Movie> movies = currentPage.getMoviesList();
            ArrayList<Movie> searchResult = new ArrayList<Movie>();
            for (Movie movie : movies) {
                if (movie.getName().startsWith(action.getStartsWith())) {
                    searchResult.add(movie);
                }
            }
            currentPage.setMoviesList(searchResult);
            output.add(objectMapper.valueToTree(new FormattedOutput(currentPage.getMoviesList(), currentUser.getUser())));
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void filter(final Action action, final CurrentPage currentPage,
                       final CurrentUser currentUser, final ArrayNode output,
                       final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("movies")) {
            ArrayList<Movie> movies = currentPage.getInput().getMovies();

            Contains contains = action.getFilters().getContains();
            if (contains != null) {
                if (contains.getGenre() != null) {
                    ArrayList<Movie> genreMovies = new ArrayList<Movie>();
                    for (Movie movie : movies) {
                        for (String genre : contains.getGenre()) {
                            if (movie.getGenres().contains(genre)) {
                                genreMovies.add(movie);
                            }
                        }
                    }
                    currentPage.setMoviesList(genreMovies);
                    movies = genreMovies;
                }

                if (contains.getActors() != null) {
                    ArrayList<Movie> actorMovies = new ArrayList<Movie>();
                    for (Movie movie : movies) {
                        for (String actor : contains.getActors()) {
                            if (movie.getActors().contains(actor)) {
                                actorMovies.add(movie);
                            }
                        }
                    }
                    currentPage.setMoviesList(actorMovies);
                }
            }

            Sort sort = action.getFilters().getSort();
            movies = currentPage.getMoviesList();
            if (sort != null) {
                if (sort.getDuration() != null) {
                    if (sort.getDuration().equals("increasing")) {
                        movies.sort(Comparator.comparingInt(Movie::getDuration).thenComparing(
                                Movie::getRating));
                    } else {
                        movies.sort(Comparator.comparingInt(Movie::getDuration).reversed().
                                thenComparing(Movie::getRating));
                    }
                }
                if (sort.getRating() != null && sort.getDuration() == null) {
                    if (sort.getRating().equals("increasing")) {
                        movies.sort(Comparator.comparingDouble(Movie::getRating));
                    } else {
                        movies.sort(Comparator.comparingDouble(Movie::getRating).reversed());
                    }
                }
            }

            ArrayList<Movie> unbannedMovies = new ArrayList<Movie>();
            for (Movie movie : movies) {
                String country = currentUser.getUser().getCredentials().getCountry();
                if (!movie.getCountriesBanned().contains(country)) {
                    unbannedMovies.add(movie);
                }
            }
            currentPage.setMoviesList(unbannedMovies);

            output.add(objectMapper.valueToTree(new FormattedOutput(currentPage.getMoviesList(), currentUser.getUser())));
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void buyTokens(final Action action, final CurrentUser currentUser,
                          final ArrayNode output, final ObjectMapper objectMapper) {
        int totalTokens = Integer.parseInt(currentUser.getUser().getCredentials().getBalance());
        int wantedTokens = Integer.parseInt(action.getCount());
        if (wantedTokens <= totalTokens) {
            currentUser.getUser().setTokensCount(currentUser.getUser().getTokensCount() + wantedTokens);
            currentUser.getUser().getCredentials().setBalance(
                    Integer.toString(totalTokens - wantedTokens));
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void buyPremiumAccount(final CurrentPage currentPage, final CurrentUser currentUser,
                                  final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("upgrades")) {
            if (currentUser.getUser().getTokensCount() > (2 * 2 * 2 + 2)) {
                currentUser.getUser().getCredentials().setAccountType("premium");
                currentUser.getUser().setTokensCount(currentUser.getUser().getTokensCount()
                        - (2 * 2 * 2 + 2));
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    static void purchase(final CurrentPage currentPage, final CurrentUser currentUser,
                         final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();
            int found = 0;
            ArrayList<Movie> movies = currentUser.getUser().getPurchasedMovies();
            for (Movie movie : movies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    found = 1;
                    break;
                }
            }

            if (found == 0) {
                if (currentUser.getUser().getCredentials().getAccountType().equals("premium")
                        && currentUser.getUser().getNumFreePremiumMovies() > 0) {
                    currentUser.getUser().decrementNumFreePremiumMovies();
                    currentUser.getUser().getPurchasedMovies().add(new Movie(currentMovie));
                    output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(), currentMovie)));
                } else if (currentUser.getUser().getTokensCount() > 2) {
                    currentUser.getUser().decrementTokensCount(2);
                    currentUser.getUser().getPurchasedMovies().add(new Movie(currentMovie));
                    output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(), currentMovie)));
                } else {
                    output.add(objectMapper.valueToTree(new FormattedOutput()));
                }
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    public static void watch(final CurrentPage currentPage, final CurrentUser currentUser,
                             final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();

            int purchased = 0;
            ArrayList<Movie> movies1 = currentUser.getUser().getPurchasedMovies();
            for (Movie movie : movies1) {
                if (movie.getName().equals(currentMovie.getName())) {
                    purchased = 1;
                    break;
                }
            }

            int watched = 0;
            ArrayList<Movie> movies2 = currentUser.getUser().getWatchedMovies();
            for (Movie movie : movies2) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            if(purchased == 0)
                output.add(objectMapper.valueToTree(new FormattedOutput()));

            if (purchased == 1 && watched == 0) {
                currentUser.getUser().getWatchedMovies().add(new Movie(currentMovie));
                output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(), currentMovie)));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    public static void like(final CurrentPage currentPage, final CurrentUser currentUser,
                            final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();

            int watched = 0;
            ArrayList<Movie> watchedMovies = currentUser.getUser().getWatchedMovies();
            for (Movie movie : watchedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            int liked = 0;
            ArrayList<Movie> likedMovies = currentUser.getUser().getLikedMovies();
            for (Movie movie : likedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    liked = 1;
                    break;
                }
            }

            if (watched == 1 && liked == 0) {
                currentMovie.incrementNumLikes();
                currentUser.getUser().getLikedMovies().add(new Movie(currentMovie));
                updateUserMovieLists(currentPage, currentMovie);
                output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(), currentMovie)));

            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    public static void rate(final Action action, final CurrentPage currentPage,
                            final CurrentUser currentUser, final ArrayNode output,
                            final ObjectMapper objectMapper) {
        if (currentPage.getName().equals("see details")) {
            Movie currentMovie = currentPage.getCurrentMovie();
            double rating = action.getRate();

            int watched = 0;
            ArrayList<Movie> watchedMovies = currentUser.getUser().getWatchedMovies();
            for (Movie movie : watchedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    watched = 1;
                    break;
                }
            }

            int rated = 0;
            int index = 0;
            ArrayList<Movie> ratedMovies = currentUser.getUser().getRatedMovies();
            for (Movie movie : ratedMovies) {
                if (movie.getName().equals(currentMovie.getName())) {
                    rated = 1;
                    break;
                }
                index++;
            }



            if (watched == 1 && (rating <= (2 * 2 + 1) && rating >= 1)) {
                if(rated == 0) {
                    double movieRating = currentMovie.getRating();
                    int movieNumRatings = currentMovie.getNumRatings();
                    currentMovie.setRating((movieRating * movieNumRatings + rating)
                            / (movieNumRatings + 1));
                    currentMovie.incrementNumRatings();
                    currentUser.getUser().getRatedMovies().add(new Movie(currentMovie));
                    currentUser.getUser().getLastRating().add(rating);
                }
                if(rated == 1) {
                    double movieRating = currentMovie.getRating();
                    int movieNumRatings = currentMovie.getNumRatings() + 1;
                    movieRating = movieRating * movieNumRatings;
                    movieRating = movieRating - currentUser.getUser().getLastRating().get(index);
                    currentMovie.setRating(movieRating);
                    currentUser.getUser().getLastRating().set(index, rating);
                }
                updateUserMovieLists(currentPage, currentMovie);

                output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser(), currentMovie)));
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }

    public static void updateUserMovieLists(final CurrentPage currentPage,
                                             final Movie currentMovie) {
        ArrayList<User> users = currentPage.getInput().getUsers();
        for (User user : users) {
            ArrayList<Movie> purchasedMovies = user.getPurchasedMovies();
            ArrayList<Movie> watchedMovies = user.getWatchedMovies();
            ArrayList<Movie> likedMovies = user.getLikedMovies();
            ArrayList<Movie> ratedMovies = user.getRatedMovies();
            for (int i = 0; i < purchasedMovies.size(); i++) {
                if (purchasedMovies.get(i).getName().equals(currentMovie.getName())) {
                    purchasedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < watchedMovies.size(); i++) {
                if (watchedMovies.get(i).getName().equals(currentMovie.getName())) {
                    watchedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < likedMovies.size(); i++) {
                if (likedMovies.get(i).getName().equals(currentMovie.getName())) {
                    likedMovies.set(i, currentMovie);
                }
            }
            for (int i = 0; i < ratedMovies.size(); i++) {
                if (ratedMovies.get(i).getName().equals(currentMovie.getName())) {
                    ratedMovies.set(i, currentMovie);
                }
            }
        }
    }
}
