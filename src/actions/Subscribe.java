package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Action;

public abstract class Subscribe {
    /**
     * method called by main which takes care of the Subscribe action
     * @param action
     * @param currentPage
     * @param currentUser
     * @param output
     */
    public static void run(final Action action, final CurrentPage currentPage,
                           final CurrentUser currentUser, final ArrayNode output,
                           final ObjectMapper objectMapper) {
        if(currentUser.getUser() != null) {
            if(currentPage.getName().equals("see details")) {
                String genre = action.getSubscribedGenre();
                if(currentPage.getCurrentMovie().getGenres().contains(genre)) {
                    if(!currentUser.getUser().getSubcribedGenres().contains(genre)) {
                        currentUser.getUser().getSubcribedGenres().add(genre);
                    } else {
                        output.add(objectMapper.valueToTree(new FormattedOutput()));
                    }
                } else {
                    output.add(objectMapper.valueToTree(new FormattedOutput()));
                }
            } else {
                output.add(objectMapper.valueToTree(new FormattedOutput()));
            }
        } else {
            output.add(objectMapper.valueToTree(new FormattedOutput()));
        }
    }
}
