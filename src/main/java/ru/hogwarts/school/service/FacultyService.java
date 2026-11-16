package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

@Service
public class FacultyService {

    private static final String FACULTY_NOT_FOUND_MESSAGE = "Факультет с таким идентификатором не найден";

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long count = 1;

    public Long addFaculty(Faculty faculty) {
        long id = count++;
        faculty.setId(id);
        faculties.put(id, faculty);
        return id;
    }

    public Faculty findFaculty(long id) {
        Faculty faculty = faculties.get(id);
        if (faculty == null) {
            throw new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE);
        }
        return faculty;
    }

    public Faculty editFaculty(Faculty faculty) {
        if (!faculties.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE);
        }
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public void deleteFaculty(long id) {
        if (faculties.remove(id) == null) {
            throw new FacultyNotFoundException(FACULTY_NOT_FOUND_MESSAGE);
        }
    }

    public Collection<Faculty> findFaculties(String color, String name) {
        return faculties.values().stream()
                .filter(faculty -> color == null || color.isBlank() || faculty.getColor().equalsIgnoreCase(color))
                .filter(faculty -> name == null || name.isBlank() || faculty.getName().equalsIgnoreCase(name))
                .toList();
    }
}
