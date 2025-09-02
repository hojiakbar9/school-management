package org.marburgermoschee.schoolmanagement.services;

import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.dtos.UpdateParentRequest;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Role;
import org.marburgermoschee.schoolmanagement.entities.User;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEntryException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.UserMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

    public List<UserDto> getAllParents(){
        List<Parent> parents = parentRepository.getAll();
        return parents.stream().map(parent -> userMapper.toDto(parent.getUser())).toList();
    }

    public UserDto getParent(Integer id){
        Parent parent = parentRepository.getParent(id).orElseThrow(
                () ->  new EntityNotFoundException("Parent not found"));
        return userMapper.toDto(parent.getUser());
    }
    public UserDto registerParent(RegisterParentRequest registerParentRequest){
        if (userRepository.existsUserByEmail(registerParentRequest.getEmail()))
            throw new DuplicateEntryException("Email already exists");
        User user = userMapper.registerParent(registerParentRequest);
        user.setPassword(passwordGenerator.generatePassword());
        user.setRole(Role.PARENT);
        userRepository.save(user);

        Parent parent = new Parent();
        parent.setUser(user);
        parentRepository.save(parent);

        return userMapper.toDto(parent.getUser());
    }

    public UserDto updateParent(
            Integer id, UpdateParentRequest request
    ){
        Parent parent = parentRepository.getParent(id).orElseThrow(
                () -> new EntityNotFoundException("Parent not found"));
        User updated = userMapper.updateParent(request, parent.getUser());
        updated.setEmail(parent.getUser().getEmail());
        updated.setPassword(parent.getUser().getPassword());
        userRepository.save(updated);
        return userMapper.toDto(updated);
    }
}
