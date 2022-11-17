package watchlist.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a movie with all its relevant parameters.
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

  private int userRating;

  /**
   * Creates a new movie object with the given parameters.
   *
   * @param name        Movie title
   * @param year        Published year
   * @param description Description of movie
   * @param keywords    Keywords to describe the movies content
   * @param rating      IMBDs registered rating
   * @param ratingCount IMDBs registered ratingcount
   * @param actors      Participating actors
   * @param directors   Directors of the movie
   * @param genre       Genre(s) of the movie
   * @param imageUrl    URL to image
   * @param thumbUrl    URL to thumb image
   * @param userRating  Users rating of the movie
   */
  @JsonCreator
  public Movie(@JsonProperty("name") String name, @JsonProperty("year") int year,
      @JsonProperty("description") String description,
      @JsonProperty("keywords") List<String> keywords,
      @JsonProperty("rating") double rating, @JsonProperty("ratingCount") int ratingCount,
      @JsonProperty("actors") List<String> actors,
      @JsonProperty("directors") List<String> directors, @JsonProperty("genre") List<String> genre,
      @JsonProperty("imageUrl") String imageUrl, @JsonProperty("thumbUrl") String thumbUrl,
      @JsonProperty("userRating") int userRating) {
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
    if (ratingCount < rating) {
      throw new IllegalArgumentException("RatingCount must be at least the same value as rating ("
          + rating + ")");
    }
    this.ratingCount = ratingCount;

    this.actors = actors;
    this.directors = directors;
    this.genre = genre;

    this.imageUrl = imageUrl;
    this.thumbUrl = thumbUrl;

    if (userRating < 1 || userRating > 10) {
      this.userRating = 0;
    } else {
      this.userRating = userRating;
    }
  }

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

  /**
   * Returns a string with 1000 (k) or 1000000 (M) appended to the ratingCount
   * if it is above 1000 or 1000000.
   * 
   * @return RatingCount as a String with " ratings" appended
   */
  public String ratingCountToString() {
    if (ratingCount == 0) {
      return "No ratings";
    }
    if (ratingCount > 1000000) {
      return (ratingCount / 1000000) + "M ratings";
    }
    if (ratingCount >= 1000) {
      return (ratingCount / 1000) + "k ratings";
    }
    return ratingCount + " ratings";
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

  public String getThumbUrl() {
    return thumbUrl;
  }

  public int getUserRating() {
    return userRating;
  }
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
   *
   * @param rating The new rating from the user
   */
  public void rate(int rating) {
    if (this.userRating == 0) {
      this.ratingCount++;
      this.rating = (this.rating * (this.ratingCount - 1) + rating) / this.ratingCount;

      this.userRating = rating;
    } else {
      updateRating(this.userRating, rating);
    }
  }

  /**
   * Updates an users rating of the movie without making a new rating.
   *
   * @param oldValue The last rating from the user
   * @param newValue The new rating from the user
   */
  private void updateRating(int oldValue, int newValue) {
    this.rating = (this.rating * this.ratingCount + (newValue - oldValue)) / this.ratingCount;

    this.userRating = newValue;
  }
  // ! Methods
}