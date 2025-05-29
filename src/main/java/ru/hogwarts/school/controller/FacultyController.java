package ru.hogwarts.school.controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/filter")
    public List<Faculty> getFacultyByNameOrColor(@RequestParam String value) {
        return facultyService.findByNameOrColorIgnoreCase(value);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsOfFaculty(@PathVariable Long id) {
        return facultyService.getStudentsByFacultyId(id);
    }
}
