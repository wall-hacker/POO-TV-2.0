package actions;

import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import actions.entities.FormattedOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Movie;
import database.Notification;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Iterator;


public abstract class Recommandation {
    /**
     * method called by main which takes care of the Subscribe action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final CurrentPage currentPage, final CurrentUser currentUser,
                           final ArrayNode output, final ObjectMapper objectMapper) {
        if (currentUser.getUser() != null && currentUser.getUser().
                getCredentials().getAccountType().equals("premium")) {
            String recommendedMovie = null;
            Notification newNotification = null;

            HashMap<String, Integer> map = new HashMap<>();
            ArrayList<Movie> likedMovies = currentUser.getUser().getLikedMovies();
            for (Movie movie : likedMovies) {
                ArrayList<String> movieGenres = movie.getGenres();
                for (String genre : movieGenres) {
                    if (map.containsKey(genre)) {
                        Integer value = map.get(genre);
                        value++;
                        map.put(genre, value);
                    } else {
                        map.put(genre, 1);
                    }
                }
            }

            TreeMap<String, Integer> treeMap = new TreeMap<>(map);

            ArrayList<Movie> sortedMovies = new ArrayList<>(currentPage.getMoviesList());
            sortedMovies.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());

            int found = 0;

            Iterator<String> itr = treeMap.keySet().iterator();
            while (itr.hasNext() && found == 0) {
                String genre = itr.next();
                for (Movie movie : sortedMovies) {
                    if (movie.getGenres().contains(genre) && !currentUser.getUser().
                            getLikedMovies().contains(movie) && found == 0) {
                        recommendedMovie = movie.getName();
                        found = 1;
                    }
                }
            }

            if (recommendedMovie != null) {
                newNotification = new Notification(recommendedMovie, "Recommendation");
            } else {
                newNotification = new Notification("No recommendation", "Recommendation");
            }
            currentUser.getUser().getNotifications().add(newNotification);
            output.add(objectMapper.valueToTree(new FormattedOutput(currentUser.getUser())));
        }
    }
}
