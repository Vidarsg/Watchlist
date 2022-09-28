package watchlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SaveLoadHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Loads a file with the given file name and creates a Watchlist object from said file
     * @param filename the String with the name of the file to load
     * @return a Watchlist object generated from the given file
     * @throws FileNotFoundException if the file does not exist
     * @throws NumberFormatException if the file is invalid
     * @throws IOException if the file is invalid
     */
    public Watchlist load(String filename) throws FileNotFoundException, NumberFormatException, IOException {
        File file = getFile(filename);
        ArrayList<Movie> movieList = objectMapper.readValue(file, new TypeReference<>(){});
        Watchlist watchlist = new Watchlist(movieList);
        return watchlist;
    }
    
    /**
     * Gets the file with the given filename
     * @param filename the String with the name of the file to get
     * @return The file with the given filename
     */
    public static File getFile(String filename) {
		return new File(SaveLoadHandler.class.getResource("savefiles/").getFile() + filename + ".json");
	}
}
