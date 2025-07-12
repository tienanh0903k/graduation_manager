package com.example.graduate.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.graduate.dto.StudentProject.StudentProjectListDTO;
import com.example.graduate.dto.Students.StudentDTO;
import com.example.graduate.models.Roles;
import com.example.graduate.models.Student;
import com.example.graduate.models.Users;
import com.example.graduate.repositories.RoleRepository;
import com.example.graduate.repositories.StudentRepository;
import com.example.graduate.repositories.UserRepository;
import com.example.graduate.service.interfaces.IStudentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


  

    // @Autowired
    // public StudentServiceImpl(StudentRepository studentRepository) {
    // this.studentRepository = studentRepository;
    // }

    @Override
    public Page<StudentProjectListDTO> searchByFilters(String classCode, String teacherName, String title,
            Pageable pageable) {
        classCode = toLikePattern(classCode);
        teacherName = toLikePattern(teacherName);
        title = toLikePattern(title);

        logger.info("Searching projects with classCode: {}, teacherName: {}, title: {}", classCode, teacherName, title);

        return studentRepository.searchByFilters(classCode, teacherName, title, pageable);
    }

    /**
     * Chuẩn hóa chuỗi tìm kiếm về dạng LIKE '%keyword%'
     * Nếu null hoặc rỗng thì trả về null.
     */
    private String toLikePattern(String input) {
        return (input == null || input.isBlank()) ? null : "%" + input.trim().toLowerCase() + "%";
    }



    /**
     * Phương thức thêm danh sách sinh viên từ mảng DTO
     */
   @Transactional
    public void addImportedStudents(List<StudentDTO> students) {
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("Student list is null or empty");
        }

        for (StudentDTO studentDTO : students) {
            if (studentRepository.existsByMssv(studentDTO.getMssv())) {
                System.out.println("Student with mssv " + studentDTO.getMssv() + " already exists. Skipping...");
                continue;
    }
            Roles role = getDefaultStudentRole();
            Users user = Users.builder()
                    .email(studentDTO.getEmail())
                    .name(studentDTO.getName())
                    .password(passwordEncoder.encode(studentDTO.getPassword()))
                    .role(role)
                    .build();
            userRepository.save(user);

            Student student = Student.builder()
                    .mssv(studentDTO.getMssv())
                    .classCode(studentDTO.getClassName())
                    .user(user)
                    .build();
            studentRepository.save(student);
        }
    }

    /**
     * Lấy role từ DTO hoặc gán mặc định nếu không có
     */
   private Roles getDefaultStudentRole() {
        return roleRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Role with id 3 not found"));
    }
}

