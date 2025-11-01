package com.example.taller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryTest {
	@Mock
	private JdbcTemplate jdbcTemplate;
	@InjectMocks
	private EmployeeRepository repository;
	@Test
	public void testSave() {
		Employee emp = new Employee("John", "Dev", 50000.0);
		when(jdbcTemplate.update(anyString(), any(), any(), any())).thenReturn(1);
		when(jdbcTemplate.queryForObject(anyString(), eq(Long.class))).thenReturn(1L);
		Employee saved = repository.save(emp);
		assert saved.getId() == 1L;
	}
	@Test
	public void testFindById() {
		when(jdbcTemplate.query(anyString(),
				ArgumentMatchers.<RowMapper<Employee>>any(),
				anyLong()))
				.thenReturn(Collections.singletonList(new Employee("John", "Dev", 50000.0)));

		Optional<Employee> found = repository.findById(1L);
		assert found.isPresent();
		assert found.get().getName().equals("John");
	}
}