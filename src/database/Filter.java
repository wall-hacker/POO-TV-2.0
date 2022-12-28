package database;

import lombok.Getter;
import lombok.Setter;

public class Filter {
    @Getter @Setter
    private Sort sort;
    @Getter @Setter
    private Contains contains;

    public Filter() {
    }
}
