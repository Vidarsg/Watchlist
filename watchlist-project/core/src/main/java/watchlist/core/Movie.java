package watchlist.core;

import java.util.ArrayList;
import java.util.List;

//import javafx.scene.image.Image;
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
    //private Image image;
    //private Image thumb;

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

        /*
        try {
            this.image = new Image(image_url);
            this.image_url = image_url;
            this.thumb = new Image(thumb_url);
            this.thumb_url = thumb_url;
        } catch (Exception e) {
            InputStream url = Movie.class.getResourceAsStream("images/coming_soon.jpeg");
            this.image = new Image(url);
            this.image_url = url.toString();
            this.thumb = new Image(url);
            this.thumb_url = url.toString();
        }*/
    }
    public Movie(String name, int year, String desc, double rating) {
        new Movie(name, year, desc, rating, null, null, null, null, null);
    }
    // Temporary to prevent errors
    public Movie(String name, int year) {
        //new Movie(name, year, "desc", 1.0, null, null, null, null, null);
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
    //public Image getImage() {return image;}
    public String getThumb_url() {return thumb_url;}
    //public Image getThumb() {return thumb;}
    // ! Getters

    // Methods
    public boolean equals(Object o) {
        if (o instanceof Movie) {
            Movie other = (Movie) o;
    
            if (other.getClass() != this.getClass()) {
                return false;
            }
    
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

    public int hashCode() {
        assert false : "hashCode not designed";
        return 1337; // any arbitrary constant will do
    }
    // ! Methods

    public String toString() {
        return name + " (" + year + ")";
    }
}
