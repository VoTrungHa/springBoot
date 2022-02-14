package com.springboot.springboot.Controllers;

import com.springboot.springboot.Models.ResponseObject;
import com.springboot.springboot.Models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/student")
public class ManagerStudentController {
    List<Student> studentList= Arrays.asList(
            new Student("1s","test1")
            ,new Student("2s","test2"));

    @GetMapping("list")
    public List<Student> GetAllStudent()
    {
        return studentList;
    }

    @PostMapping("insert")
    public ResponseEntity<ResponseObject> CreateStudent(@RequestBody Student student)
    {
        System.out.println(student);
//        studentList.add(new Student(student.getStudentId(),student.getStudentName()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200","insert student success",student));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<ResponseObject> updateStudent(@RequestBody Student student,@PathVariable("id") String id )
    {
        System.out.println(student+" "+id);

//        studentList.add(new Student(student.getStudentId(),student.getStudentName()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200","insert student success",student));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseObject> deleterStudent(@PathVariable("id") String id)
    {
        System.out.println(id);
//        studentList.add(new Student(student.getStudentId(),student.getStudentName()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200","insert student success",id));
    }

}
