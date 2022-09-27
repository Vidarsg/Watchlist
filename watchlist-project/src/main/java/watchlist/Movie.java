package watchlist;

import java.net.URL;
import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private String desc;
    private double rating;
    
    private ArrayList<String> actors = new ArrayList<String>();
    private ArrayList<String> directors = new ArrayList<String>();
    private ArrayList<String> genre = new ArrayList<String>();

    private URL image_url;
    private URL thumb_url;

    /**
     * Creates a new Movie object with the given title and year
     * @param title the title of the movie
     * @param year the release year of the movie
     */
    public Movie(String title, int year, String desc, double rating, ArrayList<String> actors, ArrayList<String> directors, ArrayList<String> genre, URL image_url, URL thumb_url) {
        if (title.isEmpty()) {throw new IllegalArgumentException("The title cannot be empty");}
        this.name = title;
        
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
        this.image_url = image_url;
    }
    public Movie(String title, int year, String desc, double rating) {
        new Movie(title, year, desc, rating, null, null, null, null, null);
    }
    // Temporary to prevent errors
    public Movie(String title, int year) {
        new Movie(title, year, "desc", 1.0, null, null, null, null, null);
    }

    // Getters
    public String getTitle() {return name;}
    public int getYear() {return year;}
    public String getDesc() {return desc;}
    public double getRating() {return rating;}

    public ArrayList<String> getActors() {return new ArrayList<String>(actors);}
    public ArrayList<String> getDirectors() {return new ArrayList<String>(directors);}
    public ArrayList<String> getGenre() {return new ArrayList<String>(genre);}

    public URL getImage_url() {return image_url;}
    public URL getThumb_url() {return thumb_url;}
    // ! Getters

    // Methods

    // ! Methods

    public String toString() {
        return name + " (" + year + ")";
    }
}
