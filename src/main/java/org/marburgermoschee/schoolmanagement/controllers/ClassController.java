package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.ClassDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterNewClassRequest;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("classes")
    public class ClassController {

    private final ClassMapper classMapper;
    private final ClassRepository classRepository;

    @PostMapping
    public ResponseEntity<ClassDto> createClass(
            @Valid @RequestBody RegisterNewClassRequest request,
            UriComponentsBuilder builder
    ){
        Class newClass = classMapper.register(request);
        System.out.println(newClass.getType());
        classRepository.save(newClass);
        URI uri = builder.path("/classes/{id}").buildAndExpand(newClass.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
