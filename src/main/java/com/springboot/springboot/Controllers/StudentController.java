package com.springboot.springboot.Controllers;

import com.springboot.springboot.Models.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    List<Student> studentList= Arrays.asList(
            new Student("1s","test1")
            ,new Student("2s","test2"));

    @GetMapping("list")
    public List<Student> GetAllStudent()
    {
        return studentList;
    }

    @GetMapping("/{id}")
    public Student GetStudentById(@PathVariable("id") String id)
    {
        return studentList.stream().filter(student -> id.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Cannot find student with id")) ;
    }

}
