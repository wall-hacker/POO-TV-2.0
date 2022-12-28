package database;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Input {
    @Getter @Setter
    private ArrayList<User> users;
    @Getter @Setter
    private ArrayList<Movie> movies;
    @Getter @Setter
    private ArrayList<Action> actions;

    /**
     * method that adds a user to the main "database"
     * @param user
     */
    public void addUser(final User user) {
        users.add(user);
    }
}
