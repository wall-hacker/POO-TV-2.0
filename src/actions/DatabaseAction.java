package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import database.Input;

public abstract class DatabaseAction {
    public void action(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) { };
    public void notify(Input input, ArrayNode output, Action action, ObjectMapper objectMapper) { };
}
