package ru.hogwarts.school.controller;



import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

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
    @GetMapping("/students/print-parallel")
    public void printStudentsInParallel() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            System.out.println("Недостаточно студентов для демонстрации.");
            return;
        }


        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());


        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });


        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }
    private synchronized void printStudentName(String name) {
        System.out.println(name);
    }
    @GetMapping("/students/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            System.out.println("Недостаточно студентов для демонстрации.");
            return;
        }


        printStudentName(students.get(0).getName());
        printStudentName(students.get(1).getName());


        Thread thread1 = new Thread(() -> {
            printStudentName(students.get(2).getName());
            printStudentName(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            printStudentName(students.get(4).getName());
            printStudentName(students.get(5).getName());
        });

        thread1.start();
        thread2.start();
    }
}