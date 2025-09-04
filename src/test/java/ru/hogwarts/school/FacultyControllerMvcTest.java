package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
@ActiveProfiles("test")
class FacultyControllerMvcTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @MockBean
    FacultyService facultyService;

    @Test
    void simplest_getById() throws Exception {
        when(facultyService.getById(5L)).thenReturn(new Faculty(5L,"Gryffindor","red"));
        mvc.perform(get("/faculties/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void simplest_create() throws Exception {
        var req = new Faculty(null,"Ravenclaw","blue");
        var saved = new Faculty(7L,"Ravenclaw","blue");
        when(facultyService.create(req)).thenReturn(saved);

        mvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7));
    }
}