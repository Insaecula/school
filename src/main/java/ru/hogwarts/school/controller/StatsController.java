package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StatsController {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StatsController(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    @GetMapping("/students/names-starting-with-a")
    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }


    @GetMapping("/students/average-age")
    public double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @GetMapping("/faculties/longest-name")
    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("Нет факультетов");
    }


    @GetMapping("/fast-sum")
    public long getFastSum() {
        long n = 1_000_000L;
        return n * (n + 1) / 2;
    }
}