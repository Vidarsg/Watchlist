package watchlist;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class User {
    private int userID;
    private String name;
    private int age;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    /**
     * @param name The users name
     * @param age The users age (must be greater than 0)
     */
    public User(String name, int age) {
        this.name = name;
        if (age<0) {throw new IllegalArgumentException("Age must be a positive integer.");}
        this.age = age;
    }

    public int getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public ArrayList<Movie> getMovies() {
        return new ArrayList<Movie>(movies);
    }
    public ArrayList<String> getMovieNames() {
        return new ArrayList<String>(movies.stream().map(x -> x.getTitle()).collect(Collectors.toList()));
    }

    /**
     * @param movie The movie to add to the users watched movies
     */
    public void watchMovie(Movie movie) {
        if (! movies.contains(movie)) {
            movies.add(movie);
        }
    }
    /**
     * @param title The title of the movie to remove from the users watched movies
     * @return True if the users watched movies contains the movie title and it gets removed
     */
    public boolean unwatchMovie(String title) {
        for (Movie m : movies) {
            if (m.getTitle().equals(title)) {
                movies.remove(m);
                return true;
            }
        }
        return false;
    }
}
