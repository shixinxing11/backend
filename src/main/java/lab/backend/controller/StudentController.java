package lab.backend.controller;

import lab.backend.model.Student;
import lab.backend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) { this.service = service; }

    @GetMapping
    public List<Student> list() { return service.list(); }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public Student create(@RequestBody Student s) { return service.save(s); }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s) { return service.update(id, s); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
