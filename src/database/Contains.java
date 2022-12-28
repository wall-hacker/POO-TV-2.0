package database;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Contains {
    @Getter @Setter
    private List<String> actors;
    @Getter @Setter
    private List<String> genre;
    @Getter @Setter
    private List<String> country;

    public Contains() {
    }
}
