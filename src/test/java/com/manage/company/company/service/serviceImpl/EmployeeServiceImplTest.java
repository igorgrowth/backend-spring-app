package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.dto.ProjectDTO;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.model.Employee;
import com.manage.company.company.model.Project;
import com.manage.company.company.model.enums.Position;
import com.manage.company.company.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepo employeeRepo;

    private Employee employee;
    private EmployeeDTO EmployeeDTO;

    @BeforeEach
    void setUp() {
        employee =
                employee.builder()
                        .id(1L)
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .email("test@mail.com")
                        .position(Position.BACKEND)
                        .project(new Project())
                        .build();

        EmployeeDTO =
                EmployeeDTO.builder()
                        .id(1L)
                        .firstName("testFirstName")
                        .lastName("testLastName")
                        .email("test@mail.com")
                        .position(Position.BACKEND)
                        .projectDTO(new ProjectDTO())
                        .build();


    }

    @Test
    void save_ShouldSaveAndReturnEmployeeDTO() {
        //When
        when(employeeRepo.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO resultEmployeeDTO = employeeService.save(EmployeeDTO);

        // Assert
        assertNotNull(resultEmployeeDTO);
        assertEquals(employee.getId(), resultEmployeeDTO.getId());
        assertEquals(employee.getEmail(), resultEmployeeDTO.getEmail());
        assertEquals(employee.getFirstName(), resultEmployeeDTO.getFirstName());
        assertEquals(employee.getLastName(), resultEmployeeDTO.getLastName());
        verify(employeeRepo, times(1)).save(employee);
    }

    @Test
    void getAll_ShouldReturnPageOfEmployeeDTO() {
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Employee employee1 = new Employee("firstName", "lastName", "email", Position.BACKEND, new Project());
        Page<Employee> employeePage = new PageImpl<>(List.of(employee, employee1), pageable, 2L);
        //When
        when(employeeRepo.findAll(pageable)).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.getAll(pageable);

        //Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(employee.getId(), result.getContent().get(0).getId());
        assertEquals(employee.getEmail(), result.getContent().get(0).getEmail());
        assertEquals(employee.getFirstName(), result.getContent().get(0).getFirstName());
        assertEquals(employee.getLastName(), result.getContent().get(0).getLastName());
        assertEquals(employee1.getId(), result.getContent().get(1).getId());
        assertEquals(employee1.getEmail(), result.getContent().get(1).getEmail());
        assertEquals(employee1.getFirstName(), result.getContent().get(1).getFirstName());
        assertEquals(employee1.getLastName(), result.getContent().get(1).getLastName());
    }

    @Test
    void getById_ShouldReturnEmployeeDTO() {

        //When
        when(employeeRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.getById(1L);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
    }

    @Test
    public void testGetById_ShouldThrowResourceNotFoundException() {
        //When
        when(employeeRepo.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(2L));
    }

    @Test
    void getByEmail_ShouldReturnEmployeeDTO() {
        String email = "test@mail.com";
        //When
        when(employeeRepo.findByEmail(email)).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.getByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
    }

    @Test
    public void testGetByEmail_ShouldThrowResourceNotFoundException() {
        String email = "test@mail.com";
        //When
        when(employeeRepo.findByEmail(email)).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getByEmail(email));
    }

    @Test
    void delete_ShouldDeleteEmployeeDTO() {
        //When
        when(employeeRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.delete(1L);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getFirstName(), result.getFirstName());
        assertEquals(employee.getLastName(), result.getLastName());
        verify(employeeRepo, times(1)).delete(employee);
    }

    @Test
    public void testDelete_NonExistingId_ShouldThrowResourceNotFoundException() {
        //When
        when(employeeRepo.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.delete(1L));

    }

    @Test
    void update_ShouldUpdateEmployeeDTO() {

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(1L, "upFirstName", "upSecondName", "up@mail.com", Position.DEVOPS, new ProjectDTO());

        //When
        when(employeeRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(employee));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO result = employeeService.update(updatedEmployeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedEmployeeDTO.getId(), result.getId());
        assertEquals(updatedEmployeeDTO.getFirstName(), result.getFirstName());
        assertEquals(updatedEmployeeDTO.getLastName(), result.getLastName());
        assertEquals(updatedEmployeeDTO.getEmail(), result.getEmail());
        assertEquals(updatedEmployeeDTO.getPosition(), result.getPosition());
        assertEquals(updatedEmployeeDTO.getProjectDTO(), result.getProjectDTO());
    }
}