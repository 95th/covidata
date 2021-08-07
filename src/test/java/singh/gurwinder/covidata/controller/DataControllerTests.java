package singh.gurwinder.covidata.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import singh.gurwinder.covidata.config.DataLoad;
import singh.gurwinder.covidata.entity.Cases;
import singh.gurwinder.covidata.repo.CasesRepo;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class DataControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DataLoad dataLoad;

    @Autowired
    private CasesRepo casesRepo;

    @Test
    public void whenGivenCase_shouldReturnDataByDate() throws Exception {
        createCase("April fool", LocalDate.of(2021, 4, 1));
        createCase("April not fool", LocalDate.of(2021, 4, 2));
        mvc.perform(get("/Get_date_info?Date=2021-04-01").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.cases[0].state", is("April fool")));
    }

    @Test
    public void whenGivenCase_shouldReturnDataByState() throws Exception {
        createCase("April fool", LocalDate.of(2021, 4, 1));
        createCase("April not fool", LocalDate.of(2021, 4, 2));
        mvc.perform(get("/Get_state_info?State_name={state}", "April fool").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.cases[0].date", is("2021-04-01")));
    }

    @Test
    public void whenGivenCase_shouldReturnDataByStateAndDate() throws Exception {
        createCase("April fool", LocalDate.of(2021, 4, 1));
        createCase("April not fool", LocalDate.of(2021, 4, 2));
        mvc.perform(get("/Pinpoint_state?State_name={state}&Date={date}", "April not fool", "2021-04-02").contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.cases[0].state", is("April not fool")))
           .andExpect(jsonPath("$.cases[0].date", is("2021-04-02")));
    }

    private void createCase(String state, LocalDate date) {
        var c = new Cases();
        c.setDate(date);
        c.setState(state);
        casesRepo.save(c);
    }
}
