package watchlist;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
    private String name;
    private int year;
    private String desc;
    private double rating;
    
    private List<String> actors = new ArrayList<String>();
    private List<String> directors = new ArrayList<String>();
    private List<String> genre = new ArrayList<String>();

    private String image_url;
    private String thumb_url;

    /**
     * Creates a new Movie object with the given title and year
     * @param name the title of the movie
     * @param year the release year of the movie
     */
    @JsonCreator
    public Movie(@JsonProperty("name") String name, @JsonProperty("year") int year, @JsonProperty("desc") String desc, @JsonProperty("rating") double rating, @JsonProperty("actors") List<String> actors, @JsonProperty("directors") List<String> directors, @JsonProperty("genre") List<String> genre, @JsonProperty("image_url") String image_url, @JsonProperty("thumb_url") String thumb_url) {
        if (name.isEmpty()) {throw new IllegalArgumentException("The title cannot be empty");}
        this.name = name;
        
        if (year < 1888) {throw new IllegalArgumentException("No motion picture was recorded before year 1888");}
        this.year = year;

        if (desc.isEmpty()) {throw new IllegalArgumentException("The description cannot be empty");}
        this.desc = desc;

        if (rating < 1 || rating > 10) {throw new IllegalArgumentException("Rating must be between 1 and 10");}
        this.rating = rating;

        this.actors = actors;
        this.directors = directors;
        this.genre = genre;

        this.image_url = image_url;
        this.thumb_url = thumb_url;
    }
    public Movie(String name, int year, String desc, double rating) {
        new Movie(name, year, desc, rating, null, null, null, null, null);
    }
    // Temporary to prevent errors
    public Movie(String name, int year) {
        this.name = name;
        this.year = year;
    }

    // Getters
    public String getName() {return name;}
    public int getYear() {return year;}
    public String getDesc() {return desc;}
    public double getRating() {return rating;}

    public List<String> getActors() {return new ArrayList<String>(actors);}
    public List<String> getDirectors() {return new ArrayList<String>(directors);}
    public List<String> getGenre() {return new ArrayList<String>(genre);}

    public String getImage_url() {return image_url;}
    public String getThumb_url() {return thumb_url;}
    // ! Getters

    // Methods
    public boolean equals(Movie other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        if (this.getName() != other.getName()) {
            return false;
        }

        if (this.getYear() != other.getYear()) {
            return false;
        }

        return true;

    }
    // ! Methods

    public String toString() {
        return name + " (" + year + ")";
    }
}
