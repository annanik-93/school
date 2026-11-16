package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

@Service
public class StudentService {

    private static final String STUDENT_NOT_FOUND_MESSAGE = "Студент с таким идентификатором не найден";

    private final Map<Long, Student> students = new HashMap<>();
    private long count = 1;

    public Long addStudent(Student student) {
        long id = count++;
        student.setId(id);
        students.put(id, student);
        return id;
    }

    public Student findStudent(long id) {
        Student student = students.get(id);
        if (student == null) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE);
        }
        return student;
    }

    public Student editStudent(Student student) {
        if (!students.containsKey(student.getId())) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE);
        }
        students.put(student.getId(), student);
        return student;
    }

    public void deleteStudent(long id) {
        if (students.remove(id) == null) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND_MESSAGE);
        }
    }

    public Collection<Student> findStudents(Integer age, String name) {
        return students.values().stream()
                .filter(student -> age == null || student.getAge() == age)
                .filter(student -> name == null || name.isBlank() || student.getName().equalsIgnoreCase(name))
                .toList();
    }
}