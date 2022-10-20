package watchlist.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/** This class represents a movie with all its relevant parameters.
 *
 * @author IT1901 gruppe 63
 */
public class Movie {
  private String name;
  private int year;
  private String description;
  private List<String> keywords;
  private double rating;
  private int ratingCount;

  private List<String> actors;
  private List<String> directors;
  private List<String> genre;

  private String imageUrl;
  private String thumbUrl;

  /**
   * Creates a new Movie object with the given title and year.
   *
   * @param name the title of the movie
   * @param year the release year of the movie
   */
  @JsonCreator
  public Movie(@JsonProperty("name") String name, @JsonProperty("year") int year,
      @JsonProperty("description") String description,
      @JsonProperty("keywords") List<String> keywords,
      @JsonProperty("rating") double rating, @JsonProperty("ratingCount") int ratingCount,
      @JsonProperty("actors") List<String> actors,
      @JsonProperty("directors") List<String> directors, @JsonProperty("genre") List<String> genre,
      @JsonProperty("imageUrl") String imageUrl, @JsonProperty("thumbUrl") String thumbUrl) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("The title cannot be empty");
    }
    this.name = name;

    if (year < 1888 || year > 2022) {
      throw new IllegalArgumentException(
          "No motion picture was recorded before year 1888 or after year 2022 as of now");
    }
    this.year = year;

    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("The description cannot be empty");
    }
    this.description = description;
    this.keywords = keywords;

    if (rating < 1 || rating > 10) {
      throw new IllegalArgumentException("Rating must be between 1 and 10");
    }
    this.rating = rating;
    if (ratingCount <= rating) {
      throw new IllegalArgumentException("RatingCount must be at least the same value as rating ("
      + rating + ")");
    }
    this.ratingCount = ratingCount;

    this.actors = actors;
    this.directors = directors;
    this.genre = genre;

    this.imageUrl = imageUrl;
    this.thumbUrl = thumbUrl;
  }

  /*
  public Movie(String name, int year, String desc, double rating, int ratingCount,
      List<String> actors,List<String> directors,List<String> genre,
      String imageUrl, String thumbUrl) {
    new Movie(name, year, desc, List.of(), rating, ratingCount,
    actors, directors, genre, imageUrl, thumbUrl);
  }*/

  // Getters
  public String getName() {
    return name;
  }

  public int getYear() {
    return year;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getKeywords() {
    return keywords;
  }

  public double getRating() {
    return (double) Math.round(rating * 100) / 100;
  }

  public int getRatingCount() {
    return ratingCount;
  }

  public List<String> getActors() {
    return new ArrayList<String>(actors);
  }

  public List<String> getDirectors() {
    return new ArrayList<String>(directors);
  }

  public List<String> getGenre() {
    return new ArrayList<String>(genre);
  }

  public String getImageUrl() {
    return imageUrl;
  }

  // public Image getImage() {return image;}
  public String getThumbUrl() {
    return thumbUrl;
  }
  // public Image getThumb() {return thumb;}
  // ! Getters

  // Methods
  /**
   * Comparing Movie objects based on name and year.
   *
   * @param o the object to compare
   * @return true if movies are equal, false if not
   */
  public boolean equals(Object o) {
    if (o instanceof Movie) {
      Movie other = (Movie) o;
      if (!this.name.equals(other.name)) {
        return false;
      }
      if (this.getYear() != other.getYear()) {
        return false;
      }
      return true;
    }
    return false;
  }

  // This method is not used, but is required by spotbugs
  public int hashCode() {
    assert false : "hashCode not designed";
    return 1337; // any arbitrary constant will do
  }

  public String toString() {
    return name + " (" + year + ")";
  }

  /**
   * Adds a rating to this movie and calculates the new rating.
   * @param rating The new rating from the user
   */
  public void rate(double rating) {
    double count = (this.ratingCount / this.rating) + 1;
    this.ratingCount += rating;
    this.rating = ratingCount / count;
  }
  // ! Methods
}

