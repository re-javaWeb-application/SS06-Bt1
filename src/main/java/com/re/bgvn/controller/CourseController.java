package com.re.bgvn.controller;

import com.re.bgvn.model.Course;
import com.re.bgvn.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ModelAttribute("activeMenu")
    public String activeMenu() {
        return "courses";
    }

    @GetMapping("/list")
    public String listCourses(@RequestParam(name = "level", defaultValue = "") String level,
                              @RequestParam(name = "maxFee", defaultValue = "999999999") BigDecimal maxFee,
                              Model model) {
        List<Course> courses = courseService.searchCourses(level, maxFee);
        model.addAttribute("courses", courses);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("maxFee", maxFee.compareTo(new BigDecimal("999999999")) == 0 ? null : maxFee);
        model.addAttribute("pageTitle", "Danh sach khoa hoc");
        return "course/list";
    }

    @GetMapping("/detail/{code}")
    public String courseDetail(@PathVariable("code") String code, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("course", courseService.getCourseByCode(code));
            model.addAttribute("pageTitle", "Chi tiet khoa hoc");
            return "course/detail";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/course/list";
        }
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("course", courseService.getCourseById(id));
            model.addAttribute("pageTitle", "Cap nhat khoa hoc");
            model.addAttribute("statuses", List.of("Sap khai giang", "Dang tuyen sinh", "Dang hoc", "Tam hoan"));
            return "course/edit";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/course/list";
        }
    }

    @PostMapping("/update")
    public String updateCourse(@ModelAttribute("course") Course course,
                               RedirectAttributes redirectAttributes) {
        courseService.updateCourse(course);
        redirectAttributes.addFlashAttribute("successMessage", "Cap nhat khoa hoc thanh cong");
        return "redirect:/course/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "Huy khoa hoc thanh cong");
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Khong the huy khoa hoc da co hoc vien dang ky");
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/course/list";
    }
}
