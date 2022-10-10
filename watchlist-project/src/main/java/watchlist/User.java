package watchlist;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class User {
    private int userID;
    private String name;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    /**
     * Creates a new User object with the given name and age
     * @param name The users name
     */
    public User(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Movie> getMovies() {
        return new ArrayList<Movie>(movies);
    }
    public ArrayList<String> getMovieNames() {
        return new ArrayList<String>(movies.stream().map(x -> x.getName()).collect(Collectors.toList()));
    }

    /**
     * Registers when the user have watched a movie
     * @param movie The movie to add to the users watched movies
     */
    public void watchMovie(Movie movie) {
        if (! movies.contains(movie)) {
            movies.add(movie);
        }
    }
    /**
     * Unregisters a movie which the user already has marked as watched
     * @param title The title of the movie to remove from the users watched movies
     * @return True if the users watched movies contains the movie title and it gets removed
     */
    public boolean unwatchMovie(String title) {
        for (Movie m : movies) {
            if (m.toString().equals(title)) {
                movies.remove(m);
                return true;
            }
        }
        return false;
    }
}
