package ru.hogwarts.school.service;

import model.Faculty;
import model.Student;
import repository.StudentRepository;

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
    }