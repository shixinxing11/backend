package lab.backend.service;

import lab.backend.model.Student;
import lab.backend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;          // 构造注入：把仓库交给服务

    public StudentService(StudentRepository repo) { this.repo = repo; }

    public List<Student> list() { return repo.findAll(); }

    public Student get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "学生不存在"));
    }

    public Student save(Student s) { return repo.save(s); }

    public Student update(Long id, Student s) {
        Student existing = get(id);
        existing.setName(s.getName());
        existing.setScore(s.getScore());
        return repo.save(existing);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
