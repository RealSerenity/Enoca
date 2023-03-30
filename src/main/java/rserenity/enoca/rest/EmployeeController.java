package rserenity.enoca.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rserenity.enoca.business.dto.EmployeeDto;
import rserenity.enoca.business.services.EmployeeServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:8080")
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    @GetMapping("/getAllEmployees")
    public List<EmployeeDto> getAllEmployees() {
        return employeeServices.getAllEmployees();
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeServices.createEmployee(employeeDto);
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name = "id") Long id) {
        return employeeServices.getEmployeeById(id);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable(name = "id") Long id, @RequestBody EmployeeDto newEmployeeDto) {
        return employeeServices.updateEmployeeById(id,newEmployeeDto);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable(name = "id") Long id) {
        return employeeServices.deleteEmployee(id);
    }

    @GetMapping("/getEmployeesOfCompany/{id}")
    public List<EmployeeDto> getEmployeesOfCompany(@PathVariable(name = "id") Long companyId) {
        return employeeServices.getEmployeesOfCompany(companyId);
    }
}
