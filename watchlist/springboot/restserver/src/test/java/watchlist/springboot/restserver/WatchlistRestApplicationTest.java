package watchlist.springboot.restserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import watchlist.core.Watchlist;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { WatchlistRestApplication.class,
    WatchlistRestController.class, WatchlistRestService.class })
@WebMvcTest
public class WatchlistRestApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() throws Exception {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testGet_watchlist() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/movies")).andExpect(
        MockMvcResultMatchers.status().isOk()).andReturn();
    try {
      Watchlist watchlist = new Watchlist();
      watchlist.setList(objectMapper.readValue(
          result.getResponse().getContentAsString(),
          new TypeReference<>() {}));
      System.out.println(watchlist.getList().get(0));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
