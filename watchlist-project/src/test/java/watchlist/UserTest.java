package watchlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    @BeforeEach
    public void setup() {
        user = new User("Test User", 20);

        movies.clear();
        movies.add(new Movie("Movie 1", 1991));
        movies.add(new Movie("Movie 2", 1992));
        movies.add(new Movie("Movie 3", 1993));
    }

    @Test
    @DisplayName("Testing adding a movie to the users watched list")
    public void testAddMovie() {
        user.addMovie(movies.get(0));
        assertEquals(movies.get(0), user.getMovies().get(0));

        // Should be the same 
        user.addMovie(movies.get(1));
        user.addMovie(movies.get(2));
        assertEquals(movies, user.getMovies());

        // Should not add the same movie
        user.addMovie(movies.get(0));
        assertEquals(movies, user.getMovies());
    }
}