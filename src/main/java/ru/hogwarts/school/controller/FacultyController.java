package ru.hogwarts.school.controller;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;
import model.Faculty;
import model.Student;
import org.springframework.stereotype.Service;


import ru.hogwarts.school.service.FacultyService;
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
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}