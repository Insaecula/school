package ru.hogwarts.school.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public List<Student> findByAgeRange(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
        public Faculty getFacultyByStudentId(Long id) {
            return studentRepository.findById(id)
                    .map(Student::getFaculty)
                    .orElse(null);
        }
    public void printStudentsInParallel() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            System.out.println("Меньше 6 студентов!");
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

    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();

        if (students.size() < 6) {
            System.out.println("Меньше 6 студентов!");
            return;
        }


        Runnable printTask1 = () -> printSynchronized(students.get(2).getName(), students.get(3).getName());
        Runnable printTask2 = () -> printSynchronized(students.get(4).getName(), students.get(5).getName());

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(printTask1).start();
        new Thread(printTask2).start();
    }

    private synchronized void printSynchronized(String name1, String name2) {
        System.out.println(name1);
        System.out.println(name2);
    }
}
