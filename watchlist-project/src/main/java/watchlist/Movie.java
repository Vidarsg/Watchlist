package watchlist;

public class Movie {
    private String title;
    private int year;

    /**
     * Creates a new Movie object with the given title and year
     * @param title the title of the movie
     * @param year the release year of the movie
     */
    public Movie(String title, int year) {
        if (title.isEmpty()) {throw new IllegalArgumentException("The title cannot be empty");}
        this.title = title;
        
        if (year < 1888) {throw new IllegalArgumentException("No motion picture was recorded before year 1888");}
        this.year = year;
    }

    public String getTitle() {
        return title;
    }
    public int getYear() {
        return year;
    }

    public String toString() {
        return title + " (" + year + ")";
    }
}
