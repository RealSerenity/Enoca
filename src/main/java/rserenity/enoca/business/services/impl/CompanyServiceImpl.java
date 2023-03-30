package rserenity.enoca.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rserenity.enoca.business.dto.CompanyDto;
import rserenity.enoca.business.services.CompanyServices;
import rserenity.enoca.data.entity.CompanyEntity;
import rserenity.enoca.data.repository.CompanyRepository;
import rserenity.enoca.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyServices {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Bu method databasedeki tüm company'leri return ediyor
    @Override
    public List<CompanyDto> getAllCompanies() {
        Iterable<CompanyEntity> entities = companyRepository.findAll();
        List<CompanyDto> dtos = new ArrayList<>();
        // Bu for döngüsünde entities içerisindeki tüm entityleri dto ya dönüştürüp listeye ekleme işlemi yapılıyor.
        for(CompanyEntity entity : entities){
            CompanyDto dto = entityToDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    // CRUD operasyonlarından oluşturma(create) işlemi.
    @Override
    public ResponseEntity<CompanyDto> createCompany(CompanyDto companyDto) {
        CompanyEntity companyEntity = dtoToEntity(companyDto);

        // Database insert
        companyEntity = companyRepository.save(companyEntity);
        companyDto = entityToDto(companyEntity);
        return ResponseEntity.ok(companyDto);
    }

    // CRUD operasyonlarından okuma(read) işlemi
    // Verilen id'ye ait company objesini return ediyor.
    @Override
    public ResponseEntity<CompanyDto> getCompanyById(Long id){
        // Verilen id'ye ait kayıt bulunamazsa Custom olarak oluşturduğum ResourceNotFoundException fırlatıyor
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Company not exist by given id : " + id));

        CompanyDto companyDto = entityToDto(companyEntity);
        return ResponseEntity.ok(companyDto);
    }

    // CRUD operasyonlarından güncelleme(update) işlemi.
    @Override
    public ResponseEntity<CompanyDto> updateCompanyById(Long id, CompanyDto newCompanyDto){
        CompanyEntity companyEntity = dtoToEntity(newCompanyDto); // ModelMapper
        CompanyEntity oldCompany = companyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not exist by given id " + id));

        // CompanyName güncelleme
        oldCompany.setCompanyName(companyEntity.getCompanyName());

        // Database insert
        CompanyEntity updatedCompany = companyRepository.save(oldCompany);

        CompanyDto companyDto = entityToDto(updatedCompany);
        return ResponseEntity.ok(companyDto);
    }

    // CRUD operasyonlarından silme(delete) işlemi.
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteCompany(Long id){
        CompanyEntity companyEntity = companyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Company not exist by given id " + id));

        //Database delete
        companyRepository.delete(companyEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    public CompanyDto entityToDto(CompanyEntity companyEntity) {
        return modelMapper.map(companyEntity, CompanyDto.class);
    }

    @Override
    public CompanyEntity dtoToEntity(CompanyDto companyDto) {
        //        companyEntity.setEmployees(getEmployeesOfCompany(companyEntity.getId()).stream().map(employeeDto -> employeeServices.dtoToEntity(employeeDto)).collect(Collectors.toSet()));
        return modelMapper.map(companyDto, CompanyEntity.class);
    }
}
