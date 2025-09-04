package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerTest {

    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;
    String U(String p){ return "http://localhost:"+port+p; }

    @Test
    void create_get_delete_minimal() {

        var toCreate = new Student(null, "Ann", 18, null);
        var created = rest.postForEntity(U("/students"), toCreate, Student.class);
        assertThat(created.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.CREATED);
        var id = created.getBody().getId();
        assertThat(id).isNotNull();


        var got = rest.getForEntity(U("/students/"+id), Student.class);
        assertThat(got.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(got.getBody().getName()).isEqualTo("Ann");


        rest.delete(U("/students/"+id));
        var after = rest.getForEntity(U("/students/"+id), Student.class);
        assertThat(after.getStatusCode().is2xxSuccessful()).isFalse();
    }
}