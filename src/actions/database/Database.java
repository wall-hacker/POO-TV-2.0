package actions.database;

import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;

public abstract class Database {
    /**
     * method called by main which takes care of the Database action
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        DatabaseAction databaseAction = DatabaseActionFactory.createDatabaseAction(
                action.getFeature());
        databaseAction.action(currentPage.getInput(), output, action, objectMapper);
        databaseAction.notify(currentPage.getInput(), output, action, objectMapper);
    }
}
