package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@ActiveProfiles("test")
class StudentControllerMvcTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @MockBean StudentService studentService;

    @Test
    void simplest_getById() throws Exception {
        when(studentService.getById(1L)).thenReturn(new Student(1L,"Ann",18,null));
        mvc.perform(get("/students/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ann"));
    }

    @Test
    void simplest_create() throws Exception {
        var req = new Student(null,"Bob",20,null);
        var saved = new Student(10L,"Bob",20,null);
        when(studentService.create(req)).thenReturn(saved);

        mvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }
}