package org.marburgermoschee.schoolmanagement.services;

import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.entities.Lesson;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEntryException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.mappers.LessonMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClassService {
    private final ClassMapper classMapper;
    private final ClassRepository classRepository;
    private final LessonMapper lessonMapper;

    public Class createClass(RegisterNewClassRequest request){
        Class newClass = classMapper.register(request);
        classRepository.save(newClass);
        return newClass;
    }

    public List<ClassWithTeachersDto> getAllClassesWithTeachers(){
        List<Class> classes =  classRepository.getAllWithTeachers();
        return classes.stream()
                .map(classMapper::toClassWithTeachersDto)
                .toList();
    }

    public ClassWithTeachersDto getClassWithTeachers(Integer id){
        Class cl = classRepository.getClassWithTeachers(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        return classMapper.toClassWithTeachersDto(cl);
    }
    public List<ClassStudentDto> getStudentsOfClass(Integer id){
        Class cl = classRepository.getClassWithStudents(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        Set<Student> students = cl.getStudents();
        return students.stream().map(classMapper::toClassStudentDto).toList();
    }
    public List<LessonDto> getClassLessons(Integer id){
        Class cl = classRepository.getClassWithLessons(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        Set<Lesson> lessons = cl.getLessons();

        return lessons.stream().map(lessonMapper::toDto).toList();
    }

    public void createLesson(Integer id, RegisterNewLessonRequest request){
        Class cl = classRepository.getClassWithLessons(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        Optional<Lesson> result = cl.getLessons()
                .stream()
                .filter(l -> l.getDate().isEqual(request.getDate()))
                .findAny();
        if(result.isPresent())
            throw new DuplicateEntryException("Lesson has already been created fot the given date");

        var lesson  = new Lesson();
        lesson.setDate(request.getDate());
        lesson.setTopic(request.getTopic());
        lesson.setClassField(cl);
        cl.getLessons().add(lesson);
        classRepository.save(cl);
    }
}
