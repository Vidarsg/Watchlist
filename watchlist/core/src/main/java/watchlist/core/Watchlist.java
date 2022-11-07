package watchlist.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the list of movies that can be watched.
 *
 * @author IT1901 gruppe 63
 */
public class Watchlist {
  private List<Movie> list;

  /**
   * Constructor for Watchlist class.
   *
   * @param list List of Movies
   */
  public Watchlist(List<Movie> list) {
    this.list = list;
  }

  /**
   * Constructor without parameters, creates empty list of movies.
   */
  public Watchlist() {
    this.list = new ArrayList<Movie>();
  }

  /**
   * Getter for this.list variable.
   *
   * @return List of Movies.
   */
  public List<Movie> getList() {
    return list;
  }

  /**
   * Set the this.list variable.
   *
   * @param list List of Movies.
   */
  public void setList(List<Movie> list) {
    this.list = list;
  }

  /**
   * Adds the given movie to the Watchlist object.
   *
   * @param movie The movie to add to the watchlist
   */
  public void addMovie(Movie movie) {
    list.add(movie);
  }

  /**
   * Removes the given movie from the Watchlist object.
   *
   * @param movie The movie to remove from the watchlist
   */
  public void removeMovie(Movie movie) {
    list.remove(movie);
  }

}
