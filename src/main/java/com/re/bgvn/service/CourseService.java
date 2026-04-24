package com.re.bgvn.service;

import com.re.bgvn.model.Course;
import com.re.bgvn.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> searchCourses(String level, BigDecimal maxFee) {
        return courseRepository.findAll().stream()
                .filter(course -> level == null || level.isBlank() || course.getLevel().equalsIgnoreCase(level))
                .filter(course -> maxFee == null || course.getFee().compareTo(maxFee) <= 0)
                .toList();
    }

    public Course getCourseByCode(String code) {
        return courseRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Khong tim thay khoa hoc voi ma " + code));
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Khong tim thay khoa hoc voi id " + id));
    }

    public void updateCourse(Course courseForm) {
        Course existing = getCourseById(courseForm.getId());
        existing.setFee(courseForm.getFee());
        existing.setStartDate(courseForm.getStartDate());
        existing.setStatus(courseForm.getStatus());
        courseRepository.update(existing);
    }

    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        if (course.getStudentCount() > 0) {
            throw new IllegalStateException("Khong the huy khoa hoc da co hoc vien dang ky");
        }
        courseRepository.deleteById(id);
    }
}
