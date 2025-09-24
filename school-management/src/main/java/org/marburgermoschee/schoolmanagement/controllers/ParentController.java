package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.UpdateParentRequest;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.services.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/parents")
public class ParentController {
    private final ParentService parentService;

    @GetMapping
    public List<UserDto> getParents(){
      return parentService.getAllParents();
    }
    @GetMapping("/{id}")
    public UserDto getParent(@PathVariable("id") Integer id){
       return parentService.getParent(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> registerParent(
            @Valid @RequestBody RegisterParentRequest registerParentRequest,
            UriComponentsBuilder builder
    ){
        UserDto userDto = parentService.registerParent(registerParentRequest);
        URI uri = builder.path("/parents/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateParent(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateParentRequest request
    ){
        return parentService.updateParent(id, request);
    }
}
