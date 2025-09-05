package ru.hogwarts.school.controller;



import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseRestTemplateIT {

    @LocalServerPort
    protected int port;

    @Autowired protected StudentRepository studentRepository;
    @Autowired protected FacultyRepository facultyRepository;

    protected String U(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void resetAndSeed() {

        studentRepository.deleteAll();
        facultyRepository.deleteAll();


        Faculty red = new Faculty();
        red.setName("Gryffindor");
        red.setColor("red");
        red = facultyRepository.save(red);

        Faculty blue = new Faculty();
        blue.setName("Ravenclaw");
        blue.setColor("blue");
        blue = facultyRepository.save(blue);


        Student s1 = new Student();
        s1.setName("S1");
        s1.setAge(15);
        s1.setFaculty(red);
        studentRepository.save(s1);

        Student s2 = new Student();
        s2.setName("S2");
        s2.setAge(20);
        s2.setFaculty(blue);
        studentRepository.save(s2);

        Student s3 = new Student();
        s3.setName("S3");
        s3.setAge(25);
        s3.setFaculty(red);
        studentRepository.save(s3);
    }
}