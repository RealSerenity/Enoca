package rserenity.enoca.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rserenity.enoca.business.dto.CompanyDto;
import rserenity.enoca.business.dto.EmployeeDto;
import rserenity.enoca.business.services.CompanyServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:8080")
public class CompanyController {

    @Autowired
    CompanyServices companyServices;

    @GetMapping("/getAllCompanies")
    public List<CompanyDto> getAllCompanies() {
       return companyServices.getAllCompanies();
    }

    @PostMapping("/createCompany")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto)  {
        return companyServices.createCompany(companyDto);
    }

    @GetMapping("/getCompany/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable(name = "id") Long id) {
        return companyServices.getCompanyById(id);
    }

    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<CompanyDto> updateCompanyById(@PathVariable(name = "id") Long id, @RequestBody CompanyDto companyDto) {
        return companyServices.updateCompanyById(id, companyDto);
    }

    @DeleteMapping("/deleteCompany/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCompany(@PathVariable(name = "id") Long id) {
        return companyServices.deleteCompany(id);
    }
}
