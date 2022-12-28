package actions.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import database.Input;

/**
 * Abstract class that is the main strategy design pattern body, this class is then
 * extended by the two subsequent strategies, DatabaseAdd and DatabaseDelete
 */
public abstract class DatabaseAction {
    /**
     * method that can either add or delete a movie in the database
     * @param input
     * @param output
     * @param action
     * @param objectMapper
     */
    public void action(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) { };

    /**
     * Observer pattern update method, that notifies the users/observers of the adding
     * or removal of a certain movie
     * @param input
     * @param output
     * @param action
     * @param objectMapper
     */
    public void notify(final Input input, final ArrayNode output, final Action action,
                       final ObjectMapper objectMapper) { };
}
