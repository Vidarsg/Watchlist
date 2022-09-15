package watchlist;

public class Movie {
    private String title;
    private int published;

    public Movie(String title, int published) {
        this.title = title;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }
    public int getPublished() {
        return published;
    }
}
