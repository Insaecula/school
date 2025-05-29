package ru.hogwarts.school.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.awt.print.Pageable;


public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Page<Avatar> findAll(Pageable pageable);

}