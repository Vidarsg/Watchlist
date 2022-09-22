package watchlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SaveLoadHandler {

    /**
     * Loads a file with the given file name and creates a Watchlist object from said file
     * @param filename the String with the name of the file to load
     * @return a Watchlist object generated from the given file
     * @throws FileNotFoundException if the file does not exist
     * @throws NumberFormatException if the file is invalid
     */
    public Watchlist load(String filename) throws FileNotFoundException, NumberFormatException {
        Watchlist watchlist = new Watchlist();
        try (Scanner scanner = new Scanner(getFile(filename))) {
            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();
                int year = Integer.parseInt(scanner.nextLine());
                Movie movie = new Movie(title, year);
                watchlist.addMovie(movie);
            }
        }
        return watchlist;
    }
    
    /**
     * Gets the file with the given filename
     * @param filename the String with the name of the file to get
     * @return The file with the given filename
     */
    public static File getFile(String filename) {
		return new File(SaveLoadHandler.class.getResource("savefiles/").getFile() + filename + ".txt");
	}
}
