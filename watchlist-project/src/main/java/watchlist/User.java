package watchlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class User {
    private int userID;
    private String name;
    private int age;
    private Collection<Movie> movies = new ArrayList<Movie>();

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
    public Collection<Movie> getMovies() {
        return new ArrayList<Movie>(movies);
    }
    public Collection<String> getMovieNames() {
        return new ArrayList<String>(movies.stream().map(x -> x.getTitle()).collect(Collectors.toList()));
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    public boolean removeMovie(String title) {
        for (Movie m : movies) {
            if (m.getTitle().equals(title)) {
                movies.remove(m);
                return true;
            }
        }
        return false;
    }
}
