package actions;

public class DatabaseActionFactory {
    public static DatabaseAction createDatabaseAction(String feature) {
        switch(feature) {
            case "add" -> {return new DatabaseAdd();}
            case "delete" -> {return new DatabaseDelete();}
            default -> throw new IllegalStateException("Unexpected value: " + feature);
        }
    }
}
