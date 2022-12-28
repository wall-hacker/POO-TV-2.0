package actions.changepage;

import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;

public abstract class ChangePage extends ChangePageAction {
    /**
     * method called by main which takes care of all the ChangePage type actions
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     * @param objectMapper
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        String page = action.getPage();
        switch (page) {
            case "login" -> login(currentPage, currentUser, output, objectMapper);
            case "register" -> register(currentPage, currentUser, output, objectMapper);
            case "logout" -> logout(currentPage, currentUser, output, objectMapper);
            case "movies" -> movies(currentPage, currentUser, output, objectMapper);
            case "see details" -> seeDetails(action, currentPage, currentUser, output,
                    objectMapper);
            case "upgrades" -> upgrades(currentPage, currentUser, output, objectMapper);
            default -> throw new IllegalStateException("Unexpected value: " + page);
        }
    }
}
