import actions.changepage.ChangePage;
import actions.entities.CurrentPage;
import actions.entities.CurrentUser;
import actions.Recommandation;
import actions.Subscribe;
import actions.database.Database;
import actions.onpage.OnPage;
import actions.back.Back;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import database.Input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

abstract class Main {
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();

        CurrentPage currentPage = new CurrentPage("homepage", input);
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        currentUser.setUser(null);

        ArrayList<Action> actions = input.getActions();
        for (Action action : actions) {
            switch (action.getType()) {
                case "change page" -> ChangePage.run(action, currentPage, currentUser, output,
                        objectMapper);
                case "on page" -> OnPage.run(action, currentPage, currentUser, output,
                        objectMapper);
                case "back" -> Back.run(action, currentPage, currentUser, output,
                        objectMapper);
                case "subscribe" -> Subscribe.run(action, currentPage, currentUser, output,
                        objectMapper);
                case "database" -> Database.run(action, currentPage, currentUser, output,
                        objectMapper);
                default -> System.out.println("easter egg");
            }
        }
        Recommandation.run(currentPage, currentUser, output, objectMapper);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
