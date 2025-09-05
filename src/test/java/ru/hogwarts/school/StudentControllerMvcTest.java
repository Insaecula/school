package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@ActiveProfiles("test")
class StudentControllerMvcTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockBean
    StudentService studentService;

    @Test
    void byAgeRange_ok() throws Exception {
        var s2 = new Student(); s2.setId(2L); s2.setName("S2"); s2.setAge(20);
        when(studentService.findByAgeRange(18, 22)).thenReturn(List.of(s2));

        mvc.perform(get("/student/by-age-range").param("min","18").param("max","22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("S2"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    void faculty_of_student_ok() throws Exception {
        var fac = new Faculty(); fac.setId(10L); fac.setName("Gryffindor"); fac.setColor("red");
        when(studentService.getFacultyByStudentId(5L)).thenReturn(fac);

        mvc.perform(get("/student/{id}/faculty", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("red"));
    }
}