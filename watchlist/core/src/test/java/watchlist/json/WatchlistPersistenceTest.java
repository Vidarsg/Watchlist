package watchlist.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import watchlist.core.Movie;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.tomakehurst.wiremock.WireMockServer;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class WatchlistPersistenceTest {

  private WatchlistPersistence watchlistPersistence = new WatchlistPersistence();
  private ObjectMapper objectMapper = new ObjectMapper();
  private ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

  private WireMockConfiguration configuration;
  private WireMockServer wireMockServer;

  private String movieResource = "test-movies";
  private List<Movie> testMovies;

  /**
   * Setup method.
   */
  @BeforeEach
  public void setup() {
    configuration = WireMockConfiguration.wireMockConfig().port(8080);
    wireMockServer = new WireMockServer(configuration.portNumber());
    wireMockServer.start();
    try (InputStream inputStream = WatchlistPersistenceTest.class
        .getResourceAsStream("test-movies.json")) {
      testMovies = Arrays.asList(objectMapper.readValue(inputStream, Movie[].class));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Testing loadMovieList")
  public void testLoadMovieList() {
    try {
      List<Movie> list = watchlistPersistence.loadMovieList(movieResource);
      assertEquals(3, list.size());
    } catch (Exception e) {
      System.out.println(WatchlistPersistence.class);
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Load movie list HTTP test.")
  public void testLoadMovieListHttp() {
    try {
      wireMockServer.stubFor(
          get("/movies")
          .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(
              objectWriter.writeValueAsString(testMovies)
            )
          )
      );
      List<Movie> list = watchlistPersistence.loadMovieListHttp();
      assertEquals(3, list.size());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @AfterEach
  public void tearDown() {
    wireMockServer.stop();
  }
}
