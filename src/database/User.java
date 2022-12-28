package database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public final class User {

    @Getter @Setter
    private Credentials credentials;
    @Getter @Setter
    private int tokensCount;
    @Getter @Setter
    private int numFreePremiumMovies = 2 * 2 * 2 * 2 - 1;
    @Getter @Setter
    private ArrayList<Movie> purchasedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> watchedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> likedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Movie> ratedMovies = new ArrayList();
    @Getter @Setter
    private ArrayList<Notification> notifications = new ArrayList();
    @Getter @Setter @JsonIgnore
    private ArrayList<String> subcribedGenres = new ArrayList();
    @Getter @Setter @JsonIgnore
    private ArrayList<Double> lastRating = new ArrayList();

    public User() {
    }
    public User(final User currentUser) {
        this.credentials = new Credentials();
        this.credentials.setName(currentUser.getCredentials().getName());
        this.credentials.setAccountType(currentUser.getCredentials().getAccountType());
        this.credentials.setBalance(currentUser.getCredentials().getBalance());
        this.credentials.setCountry(currentUser.getCredentials().getCountry());
        this.credentials.setPassword(currentUser.getCredentials().getPassword());
        this.tokensCount = currentUser.tokensCount;
        this.numFreePremiumMovies = currentUser.numFreePremiumMovies;
        this.purchasedMovies = new ArrayList();
        this.purchasedMovies.addAll(currentUser.purchasedMovies);
        this.watchedMovies = new ArrayList();
        this.watchedMovies.addAll(currentUser.watchedMovies);
        this.likedMovies = new ArrayList();
        this.likedMovies.addAll(currentUser.likedMovies);
        this.ratedMovies = new ArrayList();
        this.ratedMovies.addAll(currentUser.ratedMovies);
        this.notifications = new ArrayList();
        this.notifications.addAll(currentUser.notifications);
    }
    public User(final Credentials credentials) {
        this.credentials = new Credentials();
        this.credentials.setName(credentials.getName());
        this.credentials.setAccountType(credentials.getAccountType());
        this.credentials.setBalance(credentials.getBalance());
        this.credentials.setCountry(credentials.getCountry());
        this.credentials.setPassword(credentials.getPassword());
        this.purchasedMovies = new ArrayList();
        this.watchedMovies = new ArrayList();
        this.likedMovies = new ArrayList();
        this.ratedMovies = new ArrayList();
        this.notifications = new ArrayList();
        this.tokensCount = 0;
        this.numFreePremiumMovies = 2 * 2 * 2 * 2 - 1;
    }

    /**
     * method that decrements the number of available free premium movies
     */
    public void decrementNumFreePremiumMovies() {
        this.numFreePremiumMovies--;
    }

    /**
     * method that increments the number of available free premium movies
     */
    public void incrementNumFreePremiumMovies() {
        this.numFreePremiumMovies++;
    }

    /**
     * method that decrements the current number of tokens of a user
     */
    public void decrementTokensCount(final int numTokens) {
        this.tokensCount -= numTokens;
    }

    /**
     * method that increments the current number of tokens of a user
     */
    public void incrementTokensCount(final int numTokens) {
        this.tokensCount -= numTokens;
    }
}
