package watchlist;

import java.util.ArrayList;

public class Watchlist {
    private ArrayList<Movie> watchlist;

    public Watchlist() {
        this.watchlist = new ArrayList<>();
    }

    public ArrayList<Movie> getWatchList() {
        return watchlist;
    }

    public void addMovie(Movie movie) {
        watchlist.add(movie);
    }
}
