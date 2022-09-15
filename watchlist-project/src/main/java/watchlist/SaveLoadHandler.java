package watchlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SaveLoadHandler implements ISaveLoadHandler {

    //Denne metoden skriver spillbrettet til fil gitt av filename
    public void save(String filename, Game game) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(getFile(filename))) {
            writer.println(game.getWidth());
            writer.println(game.getHeight());
            writer.println(game.getMines());
            writer.println(game.getTotalTiles());
            writer.println(game.getOpenedTiles());
            writer.println(game.getFlags());
            writer.println(game.isGameLost());
            writer.println(game.isGameWon());
            for (int y = 0; y < game.getHeight(); y++) {
                for (int x = 0; x < game.getWidth(); x++) {
                    String tileString = "";
                    if (game.getTile(x, y).isMine()) {
                        tileString += "m";
                    }
                    if (game.getTile(x, y).isFlag()) {
                        tileString += "f";
                    }
                    if (game.getTile(x, y).isOpen()) {
                        tileString += "o";
                    }
                    tileString += game.getTile(x, y).getNeighborMines();
                    writer.println(tileString);
                }
            }
        }
	}

    //Denne metoden leser fra fil gitt av filename og oppretter et nytt spillbrett basert på informasjonen
	public Game load(String filename) throws FileNotFoundException, InputMismatchException {
		try (Scanner scanner = new Scanner(getFile(filename))) {
			int width = scanner.nextInt();
			int height = scanner.nextInt();
            int mines = scanner.nextInt();
			Game game = new Game(width, height, mines);
            int totalTiles = scanner.nextInt();
            game.setTotalTiles(totalTiles);
            int openedTiles = scanner.nextInt();
            game.setOpenedTiles(openedTiles);
            int flags = scanner.nextInt();
            game.setFlags(flags);
			if (scanner.nextBoolean()) {
				game.setGameLost();
			}
			if (scanner.nextBoolean()) {
				game.setGameWon();
			}
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
                    String tileString = scanner.next();
                    for (int i = 0; i < tileString.length(); i++) {
                        if (tileString.charAt(i) == 'm') {
                            game.getTile(x, y).setMine();
                        }
                        if (tileString.charAt(i) == 'f') {
                            game.getTile(x, y).setFlagTrue();
                        }
                        if (tileString.charAt(i) == 'o') {
                            game.getTile(x, y).setOpen();
                        }
                        if (i+1 == tileString.length()) {
                            game.getTile(x, y).setNeighborMines(Character.getNumericValue(tileString.charAt(i)));
                        }
                    }
				}
			}
			return game;
		}
	}

    public static File getFile(String filename) {
		return new File(SaveLoadHandler.class.getResource("saves/").getFile() + filename + ".txt");
	}
}
