package watchlist;

import java.util.ArrayList;

public class Watchlist {
    private ArrayList<Movie> list;

    public Watchlist() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Movie> getList() {
        return new ArrayList<Movie>(list);
    }

    /**
     * Adds the given movie to the Watchlist object
     * @param movie The movie to add to the watchlist
     */
    public void addMovie(Movie movie) {
        list.add(movie);
    }

    /**
     * Removes the given movie from the Watchlist object
     * @param movie The movie to remove from the watchlist
     */
    public void removeMovie(Movie movie) {
        list.remove(movie);
    }
}
