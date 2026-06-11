package com.school.controller;

import com.school.dto.request.GradeRequest;
import com.school.model.Grade;
import com.school.model.Teacher;
import com.school.model.User;
import com.school.repository.TeacherRepository;
import com.school.repository.UserRepository;
import com.school.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    public GradeController(GradeService gradeService,
                          UserRepository userRepository,
                          TeacherRepository teacherRepository) {
        this.gradeService = gradeService;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'PARENT')")
    public ResponseEntity<List<Grade>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Grade> createGrade(@Valid @RequestBody GradeRequest request, Authentication auth) {
        // Récupérer l'utilisateur connecté
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Trouver le teacher associé à cet utilisateur
        Teacher teacher = teacherRepository.findByUserId(user.getId()).orElse(null);
        
        // Si pas de teacher (cas ADMIN), prendre le premier teacher disponible
        Long teacherId;
        if (teacher != null) {
            teacherId = teacher.getId();
        } else {
            // Fallback: premier teacher
            teacherId = teacherRepository.findAll().stream()
                    .findFirst()
                    .map(Teacher::getId)
                    .orElseThrow(() -> new RuntimeException("Aucun enseignant trouvé. Veuillez d'abord créer un enseignant."));
        }

        return ResponseEntity.ok(gradeService.createGrade(request, teacherId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'PARENT')")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}