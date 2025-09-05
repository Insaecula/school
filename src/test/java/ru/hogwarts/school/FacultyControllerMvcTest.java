package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
@ActiveProfiles("test")
class FacultyControllerMvcTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockBean FacultyService facultyService;

    @Test
    void filter_ok() throws Exception {
        var fac = new Faculty(); fac.setId(1L); fac.setName("Gryffindor"); fac.setColor("red");
        when(facultyService.findByNameOrColorIgnoreCase("RED")).thenReturn(List.of(fac));

        mvc.perform(get("/faculty/filter").param("value", "RED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("red"));
    }

    @Test
    void students_of_faculty_ok() throws Exception {
        var s1 = new Student(); s1.setId(1L); s1.setName("S1"); s1.setAge(18);
        var s2 = new Student(); s2.setId(2L); s2.setName("S2"); s2.setAge(20);
        when(facultyService.getStudentsByFacultyId(100L)).thenReturn(List.of(s1, s2));

        mvc.perform(get("/faculty/{id}/students", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("S1"))
                .andExpect(jsonPath("$[1].name").value("S2"));
    }
}