package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("Creating student with name = {}", student.getName());
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id).orElseGet(() -> {
            logger.error("There is no student with id = {}", id);
            return null;
        });
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        if (!studentRepository.existsById(student.getId())) {
            logger.warn("Trying to update non-existent student with id = {}", student.getId());
        }
        return studentRepository.save(student);
    }
}