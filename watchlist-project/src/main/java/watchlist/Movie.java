package watchlist;

public class Movie {
    private String title;
    private int year;

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
