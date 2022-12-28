package actions.onpage;

import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;

public abstract class OnPage extends OnPageAction {
    /**
     * method called by main which takes care of all the OnPage type actions
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        String feature = action.getFeature();
        switch (feature) {
            case "login" -> login(action, currentPage, currentUser, output,
                    objectMapper);
            case "register" -> register(action, currentPage, currentUser, output,
                    objectMapper);
            case "search" -> search(action, currentPage, currentUser, output,
                    objectMapper);
            case "filter" -> filter(action, currentPage, currentUser, output,
                    objectMapper);
            case "buy tokens" -> buyTokens(action, currentUser, output,
                    objectMapper);
            case "buy premium account" -> buyPremiumAccount(currentPage, currentUser, output,
                    objectMapper);
            case "purchase" -> purchase(currentPage, currentUser, output,
                    objectMapper);
            case "watch" -> watch(currentPage, currentUser, output,
                    objectMapper);
            case "like" -> like(currentPage, currentUser, output,
                    objectMapper);
            case "rate" -> rate(action, currentPage, currentUser, output,
                    objectMapper);
            default -> throw new IllegalStateException("Unexpected value: " + feature);
        }
    }
}
