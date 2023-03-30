package rserenity.enoca.business.services;

import org.springframework.http.ResponseEntity;
import rserenity.enoca.business.dto.CompanyDto;
import rserenity.enoca.data.entity.CompanyEntity;

import java.util.List;
import java.util.Map;

public interface CompanyServices {

    public List<CompanyDto> getAllCompanies();
    public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto);
    public ResponseEntity<CompanyDto> getCompanyById(Long id);
    public ResponseEntity<CompanyDto> updateCompanyById(Long id,CompanyDto companyDto);
    public ResponseEntity<Map<String,Boolean>> deleteCompany(Long id);

    //model mapper
    public CompanyDto entityToDto(CompanyEntity companyEntity);
    public CompanyEntity dtoToEntity(CompanyDto companyDto);
}
