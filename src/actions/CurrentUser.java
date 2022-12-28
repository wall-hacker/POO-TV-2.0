package actions;

import database.User;
import lombok.Getter;
import lombok.Setter;

public final class CurrentUser {
    private static CurrentUser currentUser = null;
    @Getter @Setter
    private User user;

    private CurrentUser() {
    }

    /**
     * Singleton design pattern
     * @return
     */
    public static CurrentUser getCurrentUser() {
        if (currentUser == null) {
            currentUser = new CurrentUser();
        }
        return currentUser;
    }
}
