package org.marburgermoschee.schoolmanagement.controllers;

import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.ParentMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {
    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;

    @GetMapping
    public List<UserDto> getParents(){
        List<Parent> parents = parentRepository.getAll();
        return parents.stream().map(parentMapper::toDto).toList();
    }
    @GetMapping("/{id}")
    public UserDto getParent(@PathVariable("id") Integer id){
       Parent parent = parentRepository.getParent(id).orElseThrow(
                () ->  new EntityNotFoundException("Parent not found"));
       return parentMapper.toDto(parent);
    }

}
