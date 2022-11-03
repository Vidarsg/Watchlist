package watchlist.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** Represents the list of movies that can be watched.
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

  /**
   * Method for sorting watchlist by title.
   */
  public void sortWatchlistByName() {
    Collections.sort(list, (m1, m2) -> m1.getName().compareTo(m2.getName()));
  }

  /**
   * Method for sorting watchlist by release year.
   */
  public void sortWatchlistByYear() {
    Collections.sort(list, (m1, m2) -> Integer.compare(m1.getYear(), m2.getYear()));
  }

  /**
   * Method for sorting watchlist by rating.
   */
  public void sortWatchlistByRating() {
    Collections.sort(list, (m1, m2) -> Double.compare(m1.getRating(), m2.getRating()));
    Collections.reverse(list);
  }
  
  /**
   * Method for filtering watchlist based on genres or keywords.
   *
   * @param string Movie genre/keyword as a string.
   */
  public void filterWatchlist(String string) {
    List<Movie> filteredList = new ArrayList<Movie>();
    for (Movie movie : list) {
      if (movie.getGenre() != null) {
        for (String genre : movie.getGenre()) {
          if (genre.toLowerCase().contains(string.toLowerCase())) {
            filteredList.add(movie);
          }
        }
      }
      if (movie.getKeywords() != null) {
        for (String keyword : movie.getKeywords()) {
          if (keyword.toLowerCase().contains(string.toLowerCase())) {
            filteredList.add(movie);
          }
        }
      }
    }
    this.list = filteredList.stream().distinct().collect(Collectors.toList());
  }

}
