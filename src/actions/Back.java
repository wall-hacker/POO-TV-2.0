package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public abstract class Back extends BackAction {

    @Getter @Setter
    static ArrayList<String> pageHistory = new ArrayList<>();

    /**
     * method called by main which takes care of the Back action
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        if(currentUser != null) {
            String page = pageHistory.get(getPageHistory().size() - 2);
            if(!pageHistory.isEmpty()) {
                System.out.println(page + pageHistory.size());
                switch (page) {
                    case "hpauth" -> hpAuth(currentPage, currentUser, output, objectMapper);
                    case "login" -> login(currentPage, currentUser, output, objectMapper);
                    case "register" -> register(currentPage, currentUser, output, objectMapper);
                    case "logout" -> logout(currentPage, currentUser, output, objectMapper);
                    case "movies" -> movies(currentPage, currentUser, output, objectMapper);
                    case "see details" -> seeDetails(action, currentPage, currentUser, output, objectMapper);
                    case "upgrades" -> upgrades(currentPage, currentUser, output, objectMapper);
                    default -> throw new IllegalStateException("Unexpected value: " + page);
                }
            } else {
                output.addPOJO(new FormattedOutput());
            }
        } else {
            output.addPOJO(new FormattedOutput());
        }
    }
}
