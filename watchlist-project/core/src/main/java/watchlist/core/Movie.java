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
  private String desc;
  private double rating;

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
      @JsonProperty("desc") String desc,
      @JsonProperty("rating") double rating, @JsonProperty("actors") List<String> actors,
      @JsonProperty("directors") List<String> directors, @JsonProperty("genre") List<String> genre,
      @JsonProperty("image_url") String imageUrl, @JsonProperty("thumb_url") String thumbUrl) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("The title cannot be empty");
    }
    this.name = name;

    if (year < 1888 || year > 2022) {
      throw new IllegalArgumentException(
          "No motion picture was recorded before year 1888 or after year 2022 as of now");
    }
    this.year = year;

    if (desc.isEmpty()) {
      throw new IllegalArgumentException("The description cannot be empty");
    }
    this.desc = desc;

    if (rating < 1 || rating > 10) {
      throw new IllegalArgumentException("Rating must be between 1 and 10");
    }
    this.rating = rating;

    this.actors = actors;
    this.directors = directors;
    this.genre = genre;

    this.imageUrl = imageUrl;
    this.thumbUrl = thumbUrl;
  }

  // Getters
  public String getName() {
    return name;
  }

  public int getYear() {
    return year;
  }

  public String getDesc() {
    return desc;
  }

  public double getRating() {
    return rating;
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
  // ! Methods
}

