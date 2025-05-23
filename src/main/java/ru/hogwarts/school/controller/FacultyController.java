package ru.hogwarts.school.controller;

import model.Faculty;
import model.Student;

import java.util.List;

public class FacultyController {
    @GetMapping("/filter")
    public List<Faculty> getFacultyByNameOrColor(@RequestParam String value) {
        return facultyService.findByNameOrColorIgnoreCase(value);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsOfFaculty(@PathVariable Long id) {
        return facultyService.getStudentsByFacultyId(id);
    }
}
