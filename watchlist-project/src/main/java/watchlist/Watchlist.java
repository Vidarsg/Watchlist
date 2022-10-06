package watchlist;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    private List<Movie> list;

    public Watchlist(List<Movie> list) {
        this.list = list;
    }

    public Watchlist() {
        this.list = new ArrayList<>();
    }

    public List<Movie> getList() {
        return list;
    }

    public void setList(List<Movie> list) {
        this.list = list;
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
