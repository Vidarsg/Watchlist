package watchlist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaveLoadHandlerTest {

    private Watchlist watchlist;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

    @BeforeEach
    public void setup() {
        watchlist = new Watchlist();
    }

    @Test
    @DisplayName("Load Valid File")
    public void testLoadValid() {
        //Loading watchlist from valid file
        try {
            watchlist = saveLoadHandler.load("movies");
        } catch (FileNotFoundException e) {
            fail("The file could not be loaded. It does not exist");
        } catch (NumberFormatException e) {
            fail("The file could not be loaded. It is invalid.");
        } catch (IOException e) {
            fail("The file could not be loaded. It is invalid.");
        }

        //A few tests to make sure the loaded watchlist works as expected
        //Checks that you can add a movie to the watchlist
        Movie inception = new Movie("Inception", 2010);
        watchlist.addMovie(inception);
        assertTrue(watchlist.getList().contains(inception));

        //Checks that you can remove a movie from the watchlist
        watchlist.removeMovie(inception);
        assertFalse(watchlist.getList().contains(inception));
    }
    /*
    @Test
    @DisplayName("Load Invalid File")
    public void testLoadInvalid() {
        //Checks that exception is thrown when trying to load invalid file
        assertThrows(NumberFormatException.class, () -> {
            watchlist = saveLoadHandler.load("invalid_save");
        }, "An exception should be thrown when loading an invalid file");
    }
    */

    @Test
    @DisplayName("Load Nonexistent File")
    public void testLoadNonexistent() {
        //Checks that FileNotFoundException is thrown when trying to load nonexistent file
        assertThrows(FileNotFoundException.class, () -> {
            watchlist = saveLoadHandler.load("nonexistent_save");
        }, "FileNotFoundException should be thrown when loading a nonexistent file");
    }
}
