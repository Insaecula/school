package ru.hogwarts.school.repositories;
import model.Faculty;
import com.example.hogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorIgnoreCase(String color);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}