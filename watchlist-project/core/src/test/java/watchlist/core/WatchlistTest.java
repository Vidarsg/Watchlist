package watchlist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WatchlistTest {
    private Movie movie1, movie2;
    private Watchlist watchlist;


    @BeforeEach
    public void setup() {
        movie1 = new Movie("Unforgiven", 1992, "Retired Old West gunslinger William Munny reluctantly takes on one last job, with the help of his old partner Ned Logan and a young man", 8.2, List.of("Clint Eastwood", "Gene Hackman", "Morgan Freeman"), List.of("Clint Eastwood"), List.of("Drama", "Western"), "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BODM3YWY4NmQtN2Y3Ni00OTg0LWFhZGQtZWE3ZWY4MTJlOWU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL__QL50.jpg");
        movie2 = new Movie("Whiplash", 2014, "A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student’s potential.", 8.5, List.of("Miles Teller", "J.K. Simmons", "Melissa Benoist"), List.of("Damien Chazelle"), List.of("Drama", "Music"), "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg", "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL__QL50.jpg");
    }

    @Test
    @DisplayName("Testing the Wathclist constructor")
    public void testConstructor() {
        watchlist = new Watchlist(List.of(movie1, movie2));
        assertEquals(List.of(movie1, movie2), watchlist.getList());
    }

    @Test
    @DisplayName("Testing addMovie")
    public void testAddMovie() {
        watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1)));
        assertEquals(watchlist.getList().size(), 1);
        assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1)));

        watchlist.addMovie(movie2);
        assertEquals(watchlist.getList().size(), 2);
        assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2)));
    }

    @Test
    @DisplayName("Testing removeMovie")
    public void testRemoveMovie() {
        watchlist = new Watchlist(new ArrayList<>(Arrays.asList(movie1, movie2)));
        assertEquals(watchlist.getList().size(), 2);
        assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1, movie2)));

        watchlist.removeMovie(movie2);
        assertEquals(watchlist.getList().size(), 1);
        assertEquals(watchlist.getList(), new ArrayList<>(Arrays.asList(movie1)));
    }
}
