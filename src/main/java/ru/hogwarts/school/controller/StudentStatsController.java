package ru.hogwarts.school.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@RestController
public class StudentStatsController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentStatsController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students/count")
    public long getStudentsCount() {
        return studentRepository.getStudentsCount();
    }

    @GetMapping("/students/average-age-query")
    public double getAverageAgeQuery() {
        return studentRepository.getStudentsAverageAge();
    }

    @GetMapping("/students/last-five")
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}