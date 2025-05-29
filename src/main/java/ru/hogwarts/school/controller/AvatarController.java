package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repositories.AvatarRepository;

@RestController
public class AvatarController {

    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarController(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @GetMapping("/avatars")
    public Page<Avatar> getAvatars(@RequestParam int page, @RequestParam int size) {
        return avatarRepository.findAll(PageRequest.of(page, size));
    }
}