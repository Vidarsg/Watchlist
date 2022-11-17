package watchlist.ui;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import watchlist.core.Movie;
import watchlist.core.User;
import watchlist.core.Watchlist;
import watchlist.json.SaveLoadHandler;
import watchlist.json.WatchlistPersistence;

/**
 * Controller for app window.
 *
 * @author IT1901 gruppe 63
 */
public class WatchlistController {
  private User user;
  private Watchlist list;
  private Watchlist initialList;
  private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();
  private WatchlistPersistence persistence = new WatchlistPersistence();
  private Movie activeBrowserMovie;
  private Movie activeProfileMovie;

  @FXML
  private String movieResource;
  @FXML
  private String serverUrl;

  // BROWSER FIELDS
  @FXML
  private ListView<Movie> moviebrowser;
  @FXML
  private Text feedbackBoxBrowsing;

  @FXML
  private TextField watchMovieTitle;
  @FXML
  private Button watchMovieButton;

  // For listeners on list-items
  private ChangeListener<Movie> browserChangeListener;

  @FXML
  private TextField addMovieTitle;
  @FXML
  private TextField addMovieYear;
  @FXML
  private Label browseUsername;
  @FXML
  private ComboBox<String> browseMovieSort;
  @FXML
  private TextField browseMovieFilter;

  // Information section of the browser
  @FXML
  private AnchorPane infoBox;
  @FXML
  private ImageView infoImage;
  @FXML
  private Text infoTitle;
  @FXML
  private Text infoYear;
  @FXML
  private FlowPane infoGenre;
  @FXML
  private Text infoDesc;
  @FXML
  private Text infoRating;
  @FXML
  private Text infoDirector;
  @FXML
  private Text infoActors;

  // ! BROWSER FIELDS

  // PROFILE FIELDS
  @FXML
  private ListView<Movie> watchedMovies;
  @FXML
  private Text feedbackBoxProfile;
  @FXML
  private Label profileUsername;
  @FXML
  private ComboBox<String> profileMovieSort;

  @FXML
  private TextField unwatchMovieTitle;
  @FXML
  private Button unwatchMovieButton;

  // For listeners on list-items
  private ChangeListener<Movie> profileChangeListener;

  // Information section of the profile
  @FXML
  private AnchorPane infoBoxProfile;
  @FXML
  private ImageView infoImageProfile;
  @FXML
  private Text infoTitleProfile;
  @FXML
  private Text infoYearProfile;
  @FXML
  private FlowPane infoGenreProfile;
  @FXML
  private Text infoDescProfile;
  @FXML
  private Text infoRatingProfile;
  @FXML
  private Text infoDirectorProfile;
  @FXML
  private Text infoActorsProfile;

  @FXML
  private FlowPane ratingStars;
  @FXML
  private Slider ratingSlider;
  // ! PROFILE FIELDS

  /**
   * Runs watchlist.fxml and creates new user and watchlist object.
   */
  public void initialize() {
    user = new User("TestUser");
    list = new Watchlist();
    // handleLoadResourceListHttp();

    initialList = new Watchlist();
    handleLoadResourceList();
    initialList.sortWatchlistByRating();
    list.setList(initialList.getList());

    ObservableList<String> sortValues = FXCollections
        .observableArrayList("Rating", "Year", "Title");
    browseMovieSort.getItems().setAll(sortValues);
    browseMovieSort.getSelectionModel().selectFirst();
    profileMovieSort.getItems().setAll(sortValues);
    profileMovieSort.getSelectionModel().selectFirst();

    browseMovieFilter.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent key) {
        if (browseMovieFilter.isFocused()) {
          addFiltertoWatchlist(browseMovieFilter.getText() + key.getText());
        }
      }
    });

    ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable,
          Number oldValue, Number newValue) {
        if (activeProfileMovie != null) {
          activeProfileMovie.rate(newValue.intValue() + 1);
          updateRating(newValue.intValue());
        } else {
          ratingSlider.setDisable(true);
        }
      }
    });

    browserChangeListener = generateListener(moviebrowser, watchMovieTitle, watchMovieButton);
    profileChangeListener = generateListener(watchedMovies, unwatchMovieTitle, unwatchMovieButton);

    updateMoviebrowser();

    updateGui();
  }

  /**
   * Made for testing of the controller.
   *
   * @return The Watchlist which is represented in this instance of the app
   */
  public Watchlist getWatchlist() {
    return this.list;
  }

  /**
   * <p>
   * Used to generate event listeners to items of a ListView.
   * Creates a ChangeListener to use
   * in the methods addListener() and removeListener():
   * </p>
   *
   * <pre>
   * ListView.getSelectionModel().selectedItemProperty().addListener(ChangeListener);
   * </pre>
   *
   * <pre>
   * ListView.getSelectionModel().selectedItemProperty().removeListener(ChangeListener);
   * </pre>
   *
   * @param listView  The ListView where the items are located
   * @param textField The corresponding TextField to keep track of selected item
   * @param button    The corresponding Button to watch/unwatch movies
   * @return A ChangeListener to use in the methods addListener() and
   *         removeListener()
   */
  private ChangeListener<Movie> generateListener(
      ListView<Movie> listView, TextField textField, Button button) {
    return new ChangeListener<Movie>() {
      @Override
      public void changed(
          ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {
        // Iterate through all movies to find out which movie was clicked
        for (Movie m : list.getList()) {
          if (m != null) {
            if (m.equals(newValue)) {
              Movie activeMovie = m;

              // Set the value of the textField to the chosen Movie
              textField.setText(activeMovie.toString());

              // Enable the Watch-/Unwatch-button
              button.setDisable(false);

              if (listView.equals(watchedMovies)) {
                activeProfileMovie = newValue;
              } else {
                activeBrowserMovie = activeMovie;
              }

              updateGui();

              break;
            }
          }
        }
      }
    };
  }

  /**
   * Creates a user object.
   * Fetches username from Login.fxml and displays it in Watchlist.fxml.
   */
  public void setUsername(String name) {
    user = new User(name);
    browseUsername.setText(name);
    profileUsername.setText(name);
    handleLoadUserList();
    updateWatchedMovies();
  }

  // Methods for file handling

  /**
   * Loads the database of movies into the user interface.
   * First, the method tries to load the resource from the server.
   * If this fails, it tries to load the file locally.
   *
   * @param filename The file to load the list from.
   */
  private void handleLoadResourceList() {
    try {
      initialList.setList(persistence.loadMovieListHttp());
    } catch (Exception httpException) {
      System.err.println("Couldn't load movie resource from server.");
      try {
        initialList.setList(persistence.loadMovieList());
      } catch (Exception localLoadException) {
        System.err.println("Couldn't load movie resource locally."
            + "\nLoading movie resource failed.");
        localLoadException.printStackTrace();
      }
      httpException.printStackTrace();
    }
  }


  /**
   * Loads the users local list into the application. If no list is saved locally
   * for the user, the
   * method will create a new one.
   */
  private void handleLoadUserList() {
    try {
      user.setMovies(saveLoadHandler.loadUserListHttp(user.getName()));
    } catch (Exception httpException) {
      if (saveLoadHandler.getSaveFilePath() == null) {
        saveLoadHandler.setSaveFile(user.getName());
      }
      try {
        user.setMovies(saveLoadHandler.loadUserList());
      } catch (Exception localLoadException) {
        System.err.println("Couldn't load user list locally"
            + "Loading user list failed.");
        localLoadException.printStackTrace();
      }
      httpException.printStackTrace();
    }
    /**
    if (saveLoadHandler.getSaveFilePath() == null) {
      saveLoadHandler.setSaveFile(user.getName());
    }
    try {
      user.setMovies(saveLoadHandler.loadUserList());
    } catch (Exception e) {
      e.printStackTrace();
    }
    */
  }

  /**
   * Save the users list in the application into their local savefile.
   * If no list is saved locally for
   * the user, the method will create a new one.
   */
  private void handleSaveUserList() {
    if (saveLoadHandler.getSaveFilePath() == null) {
      saveLoadHandler.setSaveFile(user.getName());
    }
    try {
      saveLoadHandler.saveUserListHttp(user.getName(), user.getMovies());
    } catch (Exception httpException) {
      httpException.printStackTrace();
      try {
        saveLoadHandler.saveUserList(user.getMovies());
      } catch (IOException localLoadException) {
        localLoadException.printStackTrace();
      }
    }
  }

  // Handle methods for browsing

  /*
   * <i>*FXML-method*</i>
   * <p>
   * Marks a movie from the Watchlist browser as watched
   * by the user and adds it to the users personal
   * list of watched movies.
   * </p>
   */
  @FXML
  void handleWatchMovie() {
    feedbackBoxBrowsing.setText("");
    String title = watchMovieTitle.getText();
    if (title.isEmpty()) {
      feedbackBoxBrowsing.setText("Please choose a movie from the list");
    } else {
      for (Movie m : initialList.getList()) {
        if (m != null) {
          if (m.toString().equals(title)) {
            feedbackBoxBrowsing.setText("Watched movie " + m.toString());
            watchMovie(m);
          }
        }
      }
    }
  }

  /**
   * Adding a movie to the users list of watched movies.
   *
   * @param movie The movie to watch
   */
  public void watchMovie(Movie movie) {
    user.watchMovie(movie);
    updateWatchedMovies();
    handleSaveUserList();
  }

  /**
   * Method for swapping between sorting values in browserpage.
   */
  public void changeSortingInBrowser() {
    if (browseMovieSort.getValue() == "Title") {
      initialList.sortWatchlistByName();
      list.sortWatchlistByName();
    }
    if (browseMovieSort.getValue() == "Year") {
      initialList.sortWatchlistByYear();
      list.sortWatchlistByYear();
    }
    if (browseMovieSort.getValue() == "Rating") {
      initialList.sortWatchlistByRating();
      list.sortWatchlistByRating();
    }
    updateMoviebrowser();
    updateBrowserGui();
  }

  // ! Handle methods for browsing

  // Handle methods for profile

  /*
   * <i>*FXML-method*</i>
   * <p>
   * Marks a movie from the Watchlist browser as <b>not</b> watched by the user
   * and removes it from
   * the users personal list of watched movies.
   * </p>
   */
  @FXML
  void handleUnwatchMovie() {
    feedbackBoxProfile.setText("");
    String title = unwatchMovieTitle.getText();
    if (title.isEmpty()) {
      feedbackBoxProfile.setText("Please choose a movie from the list");
    } else {
      if (!user.unwatchMovie(title)) {
        feedbackBoxProfile.setText("You have not watched this movie...");
      }
    }
    updateWatchedMovies();
    handleSaveUserList();
  }

  /**
   * Method for swapping between sorting values in profilepage.
   */
  public void changeSortingInProfile() {
    if (profileMovieSort.getValue() == "Title") {
      user.sortUserlistByName();
    }
    if (profileMovieSort.getValue() == "Year") {
      user.sortUserlistByYear();
    }
    if (profileMovieSort.getValue() == "Rating") {
      user.sortUserlistByRating();
    }
    updateWatchedMovies();
    updateProfileGui();
  }

  // ! Handle methods for profile

  // Help methods for GUI

  /**
   * Updates the whole Graphical User Interface (GUI) of the application.
   */
  private void updateGui() {
    updateBrowserGui();
    updateProfileGui();
  }

  /**
   * Updates the Graphical User Interface (GUI) of the browser-part of the
   * application.
   */
  private void updateBrowserGui() {
    if (activeBrowserMovie != null) {
      showInfo(activeBrowserMovie, infoBox);
    }
  }

  /**
   * Updates the ListView of the browser-part of the application.
   */
  private void updateMoviebrowser() {
    if (list.getList().size() > 0) {

      moviebrowser.setItems(FXCollections
          .observableArrayList(
              list.getList()));
      setListeners(moviebrowser);
    }
  }

  /**
   * Updates the Graphical User Interface (GUI) of the profile-part of the
   * application.
   */
  private void updateProfileGui() {
    if (activeProfileMovie != null) {
      showInfo(activeProfileMovie, infoBoxProfile);
    }
  }

  /**
   * Updates the ListView of the profile-part of the application.
   */
  private void updateWatchedMovies() {
    if (user.getMovies().size() > 0) {
      watchedMovies.setItems(FXCollections
          .observableArrayList(
              user.getMovies()));
      setListeners(watchedMovies);
      feedbackBoxProfile.setText("");
    } else {
      watchedMovies.setItems(null);
      showInfo(null, infoBoxProfile);
      feedbackBoxProfile.setText("Nothing to show...");
    }
  }

  /**
   * <p>
   * Module to show information about movies for both browser- and profile-view.
   * </p>
   *
   * <p>
   * The pane which the movie will be displayed has to have this configuration of
   * children:
   * </p>
   *
   * <p>
   * AnchorPane {Text, Text, Label, Text, Text, Label, Text, Label, Text},
   * ImageView
   * </p>
   *
   * @param movie The movie to display
   * @param pane  The Pane where the movie should be displayed.
   *              Has to match the criterias for a display-pane.
   */
  private void showInfo(Movie movie, Pane pane) {
    if (movie == null) {
      pane.setVisible(false);
      ratingSlider.setDisable(true);
    } else {
      pane.setVisible(true);
      ratingSlider.setDisable(false);

      ObservableList<Node> children = pane.getChildren();
      ImageView img = (ImageView) children.get(1);
      img.setImage(new Image(movie.getImageUrl()));

      FlowPane box = (FlowPane) children.get(0);
      ObservableList<Node> f = box.getChildren();
      Text title = (Text) f.get(0);
      Text year = (Text) f.get(1);
      Text desc = (Text) f.get(3);

      title.setText(movie.getName());
      year.setText(String.valueOf(movie.getYear()));
      desc.setText(movie.getDescription());
      Text rating = (Text) f.get(4);
      rating.setText(movie.getRating() + "/10 ("
          + movie.ratingCountToString() + ")");

      // 5th child is a label

      StringBuilder sb = new StringBuilder();
      for (String d : movie.getDirectors()) {
        sb.append(d + ", ");
      }
      sb.deleteCharAt(sb.length() - 2);
      Text director = (Text) f.get(6);
      director.setText(sb.toString());

      // 7th child is a label

      sb = new StringBuilder();
      for (String a : movie.getActors()) {
        sb.append(a + ", ");
      }
      sb.deleteCharAt(sb.length() - 2);
      Text actors = (Text) f.get(8);
      actors.setText(sb.toString());

      FlowPane genre = (FlowPane) f.get(2);
      genre.getChildren().clear();
      if (movie.getGenre().size() > 0) {
        for (String g : movie.getGenre()) {
          Label l = new Label(g);
          l.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
              browseMovieFilter.setText(g);
              addFiltertoWatchlist(g);
            }
          });

          genre.getChildren().add(l);
          Text t = new Text(" - ");
          genre.getChildren().add(t);
        }
        genre.getChildren().remove(genre.getChildren().size() - 1);
      }
      if (pane.equals(infoBoxProfile)) {
        if (movie.getUserRating() > 0) {
          ratingSlider.setValue(movie.getUserRating() - 1);
          updateRating(movie.getUserRating() - 1);
        } else {
          updateRating(-1);
        }
      }
    }
  }

  /**
   * Updates the rating graphics.
   *
   * @param value The value to indicate with graphics
   */
  private void updateRating(int value) {
    ObservableList<Node> child = ratingStars.getChildren();
    for (int i = 0; i < child.size(); i++) {
      if (child.get(i).getClass().equals(SVGPath.class)) {
        if (i <= value) {
          child.get(i).setStyle("-fx-fill: #ff0;");
        } else {
          child.get(i).setStyle("-fx-fill: #0000;");
        }
      }
    }
    handleSaveUserList();
  }

  /**
   * Used to add listeners to a listView based on which ListView it is being
   * called upon.
   *
   * @param listView The ListView where the listeners are to be set to each list
   *                 item
   */
  private void setListeners(ListView<Movie> listView) {
    if (listView.equals(watchedMovies)) {
      listView.getSelectionModel().selectedItemProperty().removeListener(profileChangeListener);
      listView.getSelectionModel().selectedItemProperty().addListener(profileChangeListener);
    } else {
      listView.getSelectionModel().selectedItemProperty().removeListener(browserChangeListener);
      listView.getSelectionModel().selectedItemProperty().addListener(browserChangeListener);
    }
  }

  /**
   * Adds a new filter to the watchlist.
   */
  public void addFiltertoWatchlist(String filter) {
    list.setList(initialList.filterWatchlist(filter));
    updateMoviebrowser();
    updateGui();
  }

  // ! Help methods for GUI

}
