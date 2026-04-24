package com.re.bgvn.repository;

import com.re.bgvn.model.Course;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private static final List<Course> COURSES = new ArrayList<>();

    static {
        COURSES.add(new Course(1L, "ENG-BEGIN-01", "English Foundation", "Beginner",
                new BigDecimal("3500000"), LocalDate.of(2026, 5, 10),
                "Xay dung nen tang tu vung, phat am va giao tiep co ban cho nguoi moi bat dau.",
                "Ms. Linh Tran", "12 tuan", 0, 20, "Sap khai giang"));
        COURSES.add(new Course(2L, "IELTS-5.5", "IELTS Target 5.5", "Intermediate",
                new BigDecimal("5200000"), LocalDate.of(2026, 5, 18),
                "Lo trinh luyen 4 ky nang IELTS voi de mock test va chua bai hang tuan.",
                "Mr. David Nguyen", "14 tuan", 12, 20, "Dang tuyen sinh"));
        COURSES.add(new Course(3L, "IELTS-6.5", "IELTS Intensive 6.5", "Advanced",
                new BigDecimal("7800000"), LocalDate.of(2026, 6, 2),
                "Tap trung chien luoc lam bai, viet task 2, speaking feedback 1-1.",
                "Ms. Ha Pham", "16 tuan", 18, 18, "Gan du hoc vien"));
        COURSES.add(new Course(4L, "BUS-COM-01", "Business Communication", "Intermediate",
                new BigDecimal("4600000"), LocalDate.of(2026, 5, 25),
                "Ren luyen email, thuyet trinh va hop tieng Anh cho moi truong cong so.",
                "Mr. Jason Le", "10 tuan", 8, 15, "Dang hoc"));
        COURSES.add(new Course(5L, "KIDS-START-01", "Kids Starter", "Beginner",
                new BigDecimal("2800000"), LocalDate.of(2026, 6, 8),
                "Chuong trinh tieng Anh thieu nhi ket hop tro choi, am nhac va phonics.",
                "Ms. Thao Vo", "8 tuan", 15, 20, "Dang tuyen sinh"));
        COURSES.add(new Course(6L, "TOEIC-750", "TOEIC Bridge 750+", "Advanced",
                new BigDecimal("6400000"), LocalDate.of(2026, 6, 15),
                "Tang toc ky nang lam bai TOEIC LC/RC va bo sung tu vung hoc thuat.",
                "Mr. Minh Do", "12 tuan", 0, 25, "Sap khai giang"));
    }

    public List<Course> findAll() {
        return new ArrayList<>(COURSES);
    }

    public Optional<Course> findById(Long id) {
        return COURSES.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst();
    }

    public Optional<Course> findByCode(String code) {
        return COURSES.stream()
                .filter(course -> course.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public void update(Course updatedCourse) {
        findById(updatedCourse.getId()).ifPresent(existing -> {
            existing.setFee(updatedCourse.getFee());
            existing.setStartDate(updatedCourse.getStartDate());
            existing.setStatus(updatedCourse.getStatus());
        });
    }

    public boolean deleteById(Long id) {
        return COURSES.removeIf(course -> course.getId().equals(id));
    }
}
