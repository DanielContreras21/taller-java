package com.example.taller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository repository;
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return repository.save(employee);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> read(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Employee
            employee) {
        employee.setId(id);
        int updated = repository.update(employee);
        if (updated == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int deleted = repository.deleteById(id);
        if (deleted == 0) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
}
