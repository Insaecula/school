package ru.hogwarts.school.controller;

import model.Faculty;
import model.Student;
import service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/by-age-range")
    public List<Student> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeRange(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        return studentService.getFacultyByStudentId(id);
    }
}