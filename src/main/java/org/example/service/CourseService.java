package org.example.service;

import org.example.dto.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    // Crie um service com os métodos CRUD para Course

    public List<Course> getAllCourses() {
        return courses;
    }

    // Retrieve a course by its ID
    public Optional<Course> getCourseById(int id) {
        return courses
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public boolean updateCourse(int id, Course newCourse) {
        return getCourseById(id).map(existingCourse -> {
            courses.remove(existingCourse);
            courses.add(newCourse);
            return true;
        }).orElse(false);
    }

    public boolean deleteCourse(int id) {
        return courses
                .removeIf(c -> c.getId() == id);
    }




}
