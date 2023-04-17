package com.manage.company.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.entity.enums.Position;
import com.manage.company.company.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = EmployeeController.class, useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = EmployeeController.class)})
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private EmployeeDTO testEmployeeDTO(){
        return  EmployeeDTO.builder()
                .id(1L)
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("test@mail.com")
                .position(Position.BACKEND)
                .build();
    }

    @Test
    void createEmployee_ShouldCreateNewEmployee() throws Exception {
        //Setup
        EmployeeDTO employeeDTO = testEmployeeDTO();
        //Whrn
        when(employeeService.save(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        String requestJson = objectMapper.writeValueAsString(employeeDTO);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("testLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@mail.com"));
    }

    @Test
    void getEmployees_ShouldReturnListOfEmployees() throws Exception {
        //Setup
        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeDTO> employeesPage = new PageImpl<>(Arrays.asList(testEmployeeDTO()), pageable, 1);
        when(employeeService.getAll(pageable)).thenReturn(employeesPage);

        // Perform GET request
        mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName").value("testLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value("test@mail.com"));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeDTO() throws Exception {
        //Setup
        Long employeeId = 1L;
        EmployeeDTO expectedEmployee = testEmployeeDTO();

        // When
        when(employeeService.getById(eq(employeeId))).thenReturn(expectedEmployee);

        MvcResult result = mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        EmployeeDTO actualEmployee = objectMapper.readValue(responseBody, EmployeeDTO.class);
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployeeDTO() throws Exception {
        //Setup
        Long employeeId = 1L;
        EmployeeDTO expectedDeletedEmployee = testEmployeeDTO();

        // When
        when(employeeService.delete(eq(employeeId))).thenReturn(expectedDeletedEmployee);


        MvcResult result = mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        EmployeeDTO actualDeletedEmployee = objectMapper.readValue(responseBody, EmployeeDTO.class);
        assertEquals(expectedDeletedEmployee, actualDeletedEmployee);
    }

    @Test
    void getEmployeeByEmail_ShouldReturnEmployeeDTO() throws Exception {
        //Setup
        String email = "test@mail.com";
        EmployeeDTO expectedEmployee = testEmployeeDTO();

        //When
        when(employeeService.getByEmail(eq(email))).thenReturn(expectedEmployee);

        MvcResult result = mockMvc.perform(get("/api/employees/byEmail/{email}", email))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        EmployeeDTO actualEmployee = objectMapper.readValue(responseBody, EmployeeDTO.class);
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void testUpdateEmployee_ShouldUpdateEmployeeDTO() throws Exception {
        //Setup
        Long employeeId = 1L;
        EmployeeDTO updatedEmployee = testEmployeeDTO();
        updatedEmployee.setFirstName("updatedFirstName");

        //When
        when(employeeService.update(any(EmployeeDTO.class))).thenReturn(updatedEmployee);

        MvcResult result = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String responseBody = result.getResponse().getContentAsString();
        EmployeeDTO actualEmployee = objectMapper.readValue(responseBody, EmployeeDTO.class);
        assertEquals(updatedEmployee, actualEmployee);
    }
}