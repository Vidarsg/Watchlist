package watchlist;

import java.util.ArrayList;

public class Watchlist {
    private ArrayList<Movie> list;

    public Watchlist() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Movie> getList() {
        return list;
    }

    public void addMovie(Movie movie) {
        list.add(movie);
    }
}
