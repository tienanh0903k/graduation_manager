package com.example.graduate.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.dto.Students.SearchStudentDTO;
import com.example.graduate.dto.Students.StudentDTO;
import com.example.graduate.models.Student;
import com.example.graduate.response.ResponseObject;
import com.example.graduate.service.interfaces.IStudentService;


@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }


    /**
     * get list project for role user (student)
     * @param classCode
     * @param teacherName
     * @param title
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Page<StudentProjectListDTO>> searchProjects(
            @RequestParam(required = false) String classCode,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentProjectListDTO> results = studentService.searchByFilters(classCode, teacherName, title,
                PageRequest.of(page, size));
        return ResponseEntity.ok(results);
    }


    //     @PostMapping("/import")
    // public ResponseEntity<String> importStudents(@RequestBody ArrayList<StudentDTO> students) {
    //     try {
    //         System.out.println("Received raw students: " + students);
    //         if (students == null) {
    //             System.out.println("Students is null");
    //             return ResponseEntity.badRequest().body("Student list is null");
    //         }
    //         if (students.isEmpty()) {
    //             System.out.println("Students is empty");
    //             return ResponseEntity.badRequest().body("Student list is empty. Please provide at least one student.");
    //         }
    //         System.out.println("Students size: " + students.size() + ", Data: " + students);
    //         studentService.addImportedStudents(students);
    //         return ResponseEntity.ok("Imported successfully");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Error importing students: " + e.getMessage());
    //     }
    // }

    @PostMapping("/import")
    public ResponseEntity<ResponseObject<String>> importStudents(@RequestBody List<StudentDTO> students) {
        if (students == null || students.isEmpty()) {
            ResponseObject<String> response = ResponseObject.error("Student list is null or empty",
                    HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(response);
        }

        studentService.addImportedStudents(students);

        ResponseObject<String> response = ResponseObject.success("Imported successfully", null);
        return ResponseEntity.ok(response);
    }



    /**
     * search student using pageable for admin
     * @param names
     * @return
     */
    @PostMapping("/search-list")
    public ResponseEntity<Page<Student>> searchStudents(
            @RequestBody SearchStudentDTO searchStudentDTO,
            Pageable pageable
        ) 
    { 
        return ResponseEntity.ok(studentService.searchStudents(searchStudentDTO, pageable));
    }



    @PostMapping("/test-string-list")
    public ResponseEntity<?> testList(@RequestBody List<String> names) {
        System.out.println("NAMES: " + names);
        return ResponseEntity.ok(names);
    }

}
