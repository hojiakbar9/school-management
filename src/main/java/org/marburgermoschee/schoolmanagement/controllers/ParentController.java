package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.UpdateParentRequest;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Role;
import org.marburgermoschee.schoolmanagement.entities.User;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEmailException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.UserMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.services.PasswordGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {
    private final ParentRepository parentRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

    @GetMapping
    public List<UserDto> getParents(){
        List<Parent> parents = parentRepository.getAll();
        return parents.stream().map(parent -> userMapper.toDto(parent.getUser())).toList();
    }
    @GetMapping("/{id}")
    public UserDto getParent(@PathVariable("id") Integer id){
       Parent parent = parentRepository.getParent(id).orElseThrow(
                () ->  new EntityNotFoundException("Parent not found"));
       return userMapper.toDto(parent.getUser());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> registerParent(
            @Valid @RequestBody RegisterParentRequest registerParentRequest,
            UriComponentsBuilder builder
    ){
        if (userRepository.existsUserByEmail(registerParentRequest.getEmail()))
            throw new DuplicateEmailException();
        User user = userMapper.registerParent(registerParentRequest);
        user.setPassword(passwordGenerator.generatePassword());
        user.setRole(Role.PARENT);
        userRepository.save(user);

        Parent parent = new Parent();
        parent.setUser(user);
        parentRepository.save(parent);

        UserDto userDto = userMapper.toDto(parent.getUser());
        URI uri = builder.path("/parents/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateParent(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateParentRequest request
    ){
        Parent parent = parentRepository.getParent(id).orElseThrow(
                () -> new EntityNotFoundException("Parent not found"));
        User updated = userMapper.updateParent(request, parent.getUser());
        updated.setEmail(parent.getUser().getEmail());
        updated.setPassword(parent.getUser().getPassword());
        userRepository.save(updated);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
}
