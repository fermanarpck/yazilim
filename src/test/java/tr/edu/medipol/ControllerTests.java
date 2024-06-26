package tr.edu.medipol;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.DirtiesContext;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        Controller.Tasks.clear();
        Controller.Tasks.put(1, new ArrayList<>());
        Controller.Tasks.put(2, new ArrayList<>());
    }

    @Test
    public void listAllTasksTest() throws Exception {
        mockMvc.perform(get("/taskList"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['1']", hasSize(0)))
                .andExpect(jsonPath("$['2']", hasSize(0)));

    }

    @Test
    public void addTaskTest() throws Exception {
        Task newTask = new Task("Test Task");

        mockMvc.perform(post("/addTask/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newTask)))
            .andExpect(status().isOk())
            .andExpect(content().string("Task added successfully"));

        mockMvc.perform(get("/taskList"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['1']", hasSize(1)))
                .andExpect(jsonPath("$['1'][0]", is("Test Task")));
    }
}
