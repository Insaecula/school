package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FacultyControllerTest {

    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;
    String U(String p){ return "http://localhost:"+port+p; }

    @Test
    void create_get_update_minimal() {

        var created = rest.postForEntity(U("/faculties"),
                new Faculty(null, "Gryffindor", "red"), Faculty.class);
        assertThat(created.getStatusCode()).isIn(HttpStatus.OK, HttpStatus.CREATED);
        var id = created.getBody().getId();


        var got = rest.getForEntity(U("/faculties/"+id), Faculty.class);
        assertThat(got.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(got.getBody().getName()).isEqualTo("Gryffindor");


        rest.put(U("/faculties/"+id), new Faculty(id, "NewName", "red"));
        var after = rest.getForEntity(U("/faculties/"+id), Faculty.class);
        assertThat(after.getBody().getName()).isEqualTo("NewName");
    }
}