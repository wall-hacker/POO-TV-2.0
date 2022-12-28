package database;

import lombok.Getter;
import lombok.Setter;

public class Credentials {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String accountType;
    @Getter @Setter
    private String country;
    @Getter @Setter
    private String balance;

    public Credentials() {
    }
}
