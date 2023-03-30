package rserenity.enoca.business.services;

import org.springframework.http.ResponseEntity;
import rserenity.enoca.business.dto.EmployeeDto;
import rserenity.enoca.data.entity.EmployeeEntity;

import java.util.List;
import java.util.Map;

public interface EmployeeServices {

    public List<EmployeeDto> getAllEmployees();
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto);
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id);
    public ResponseEntity<EmployeeDto> updateEmployeeById(Long id,EmployeeDto employeeDto);
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(Long id);

    public List<EmployeeDto> getEmployeesOfCompany(Long companyId);

    //model mapper
    public EmployeeDto entityToDto(EmployeeEntity customerEntity);
    public EmployeeEntity dtoToEntity(EmployeeDto customerDto);
}
