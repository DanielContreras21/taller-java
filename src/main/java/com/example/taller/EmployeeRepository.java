package com.example.taller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Employee save(Employee employee) {
        String sql = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getPosition(),
                employee.getSalary());
        Long id = jdbcTemplate.queryForObject("SELECT last_insert_rowid()",
                Long.class);
        employee.setId(id);
        return employee;
    }
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee emp = new Employee();
            emp.setId(rs.getLong("id"));
            emp.setName(rs.getString("name"));
            emp.setPosition(rs.getString("position"));
            emp.setSalary(rs.getDouble("salary"));
            return emp;
        }, id);
        return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0));
    }
    public int update(Employee employee) {
        String sql = "UPDATE employees SET name=?, position=?, salary=? WHERE id=?";
        return jdbcTemplate.update(sql, employee.getName(), employee.getPosition(),
                employee.getSalary(), employee.getId());
    }
    public int deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee emp = new Employee();
            emp.setId(rs.getLong("id"));
            emp.setName(rs.getString("name"));
            emp.setPosition(rs.getString("position"));
            emp.setSalary(rs.getDouble("salary"));
            return emp;
        });
    }
}
