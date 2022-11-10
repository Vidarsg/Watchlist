package watchlist.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Represents a user of the watchlist app.
 *
 * @author IT1901 gruppe 63.
 */
public class User {
  private String name;
  private List<Movie> movies = new ArrayList<Movie>();
  private List<Movie> filteredMovies = new ArrayList<Movie>();

  /**
   * Creates a new User object with the given name.
   *
   * @param name The users name.
   */
  public User(String name) {
    this.name = name;
  }

  /**
   * Getter for username.
   *
   * @return name of user, a String.
   */
  public String getName() {
    return name;
  }

  /**
   * Get user's movie list.
   *
   * @return movies, a List of Movie objects.
   */
  public List<Movie> getMovies() {
    return movies;
  }

  /**
   * Get user's filtered movie list.
   *
   * @return filtered movies, a List of Movie objects.
   */
  public List<Movie> getFilteredMovies() {
    return filteredMovies;
  }

  /**
   * Set the user's watched movies.
   *
   * @param movies List of Movie objects.
   */
  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }

  /**
   * Get names of user's watched movies.
   *
   * @return An ArrayList of Strings containing movie names.
   */
  public ArrayList<String> getMovieNames() {
    return new ArrayList<String>(movies.stream().map(x -> x.getName()).collect(
    Collectors.toList()));
  }

  /**
   * Registers when the user have watched a movie.
   *
   * @param movie The movie to add to the users watched movies.
   */
  public void watchMovie(Movie movie) {
    if (!movies.contains(movie)) {
      movies.add(movie);
    }
  }

  /**
   * Unregisters a movie which the user already has marked as watched.
   *
   * @param title The title of the movie to remove from the users watched movies.
   * @return True if the users watched movies contains the movie title and it gets removed.
   */
  public boolean unwatchMovie(String title) {
    for (Movie m : movies) {
      if (m != null) {
        if (m.toString().equals(title)) {
          movies.remove(m);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Method for sorting userlist by title.
   */
  public void sortUserlistByName() {
    Collections.sort(movies, (m1, m2) -> m1.getName().compareTo(m2.getName()));
  }

  /**
   * Method for sorting userlist by release year.
   */
  public void sortUserlistByYear() {
    Collections.sort(movies, (m1, m2) -> Integer.compare(m1.getYear(), m2.getYear()));
  }

  /**
   * Method for sorting userlist by rating.
   */
  public void sortUserlistByRating() {
    Collections.sort(movies, (m1, m2) -> Double.compare(m1.getRating(), m2.getRating()));
    Collections.reverse(movies);
  }

}
