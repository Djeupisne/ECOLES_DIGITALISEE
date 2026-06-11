package com.school.service;

import com.school.dto.request.GradeRequest;
import com.school.exception.ResourceNotFoundException;
import com.school.model.Grade;
import com.school.model.Student;
import com.school.model.Teacher;
import com.school.repository.GradeRepository;
import com.school.repository.StudentRepository;
import com.school.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public GradeService(GradeRepository gradeRepository,
                        StudentRepository studentRepository,
                        TeacherRepository teacherRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade createGrade(GradeRequest request, Long teacherId) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant introuvable"));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant introuvable"));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setTeacher(teacher);
        grade.setSubject(request.getSubject());
        grade.setScore(request.getScore());
        grade.setTerm(request.getTerm());

        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}