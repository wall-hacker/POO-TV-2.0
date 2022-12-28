package actions.database;

public abstract class DatabaseActionFactory {
    /**
     * The actual factory method which return a different strategy
     * based on the database action feature
     * @param feature
     * @return
     */
    public static DatabaseAction createDatabaseAction(final String feature) {
        switch (feature) {
            case "add" -> {
                return new DatabaseAdd();
            }
            case "delete" -> {
                return new DatabaseDelete();
            }
            default -> throw new IllegalStateException("Unexpected value: " + feature);
        }
    }
}
