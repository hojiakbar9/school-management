package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.MarkAttendanceRequest;
import org.marburgermoschee.schoolmanagement.services.LessonService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/{id}/attendances")
    public void markAttendance(
            @PathVariable("id") Integer lessonId,
            @Valid @RequestBody MarkAttendanceRequest request){
    lessonService.markAttendance(lessonId, request);
    }
}
