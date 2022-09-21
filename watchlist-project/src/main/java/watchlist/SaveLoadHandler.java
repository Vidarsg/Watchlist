package watchlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SaveLoadHandler {

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
    
    public static File getFile(String filename) {
		return new File(SaveLoadHandler.class.getResource("savefiles/").getFile() + filename + ".txt");
	}
}
