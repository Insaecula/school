package ru.hogwarts.school;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import ru.hogwarts.school.controller.BaseRestTemplateIT;
import ru.hogwarts.school.model.Faculty;


import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StudentControllerTest extends BaseRestTemplateIT {

    @Autowired TestRestTemplate rest;

    @Test
    void byAgeRange_returns_only_students_in_range() {
        var resp = rest.exchange(
                U("/student/by-age-range?min=18&max=22"),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Map<String,Object>>>() {}
        );

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(resp.getBody()).hasSize(1);
        assertThat(resp.getBody().get(0).get("name")).isEqualTo("S2");
    }

    @Test
    void faculty_by_studentId_returns_faculty() {

        Long anyStudentId = studentRepository.findAll().get(0).getId();

        var resp = rest.getForEntity(U("/student/" + anyStudentId + "/faculty"), Faculty.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();

        assertThat(resp.getBody().getName()).isIn("Gryffindor", "Ravenclaw");
    }
}