package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Role;
import org.marburgermoschee.schoolmanagement.entities.User;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEmailException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.ParentMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.services.PasswordGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {
    private final ParentRepository parentRepository;
    private final ParentMapper parentMapper;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

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

    @PostMapping
    @Transactional
    public void registerParent(
            @Valid @RequestBody RegisterParentRequest registerParentRequest){
        if (userRepository.existsUserByEmail(registerParentRequest.getEmail()))
            throw new DuplicateEmailException();
        User user = parentMapper.register(registerParentRequest);
        user.setPassword(passwordGenerator.generatePassword());
        user.setRole(Role.PARENT);
        userRepository.save(user);

        Parent parent = new Parent();
        parent.setUser(user);
        parentRepository.save(parent);
    }
}
