package rserenity.enoca.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rserenity.enoca.business.dto.EmployeeDto;
import rserenity.enoca.business.services.CompanyServices;
import rserenity.enoca.business.services.EmployeeServices;
import rserenity.enoca.data.entity.EmployeeEntity;
import rserenity.enoca.data.repository.EmployeeRepository;
import rserenity.enoca.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyServices companyServices;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Bu method databasedeki tüm employee'leri return ediyor
    @Override
    public List<EmployeeDto> getAllEmployees() {
        Iterable<EmployeeEntity> entities = employeeRepository.findAll();
        List<EmployeeDto> dtos = new ArrayList<>();
        // Bu for döngüsünde entities içerisindeki tüm entityleri dto ya dönüştürüp listeye ekleme işlemi yapılıyor.
        for(EmployeeEntity entity : entities){
            EmployeeDto dto = entityToDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    // CRUD operasyonlarından oluşturma(create) işlemi.
    @Override
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = dtoToEntity(employeeDto);

        // Passwordu encode edip database'e kaydediyorum
        employeeEntity.setPassword(passwordEncoder.encode(employeeEntity.getPassword()));

        //Database insert
        employeeEntity = employeeRepository.save(employeeEntity);
        employeeDto = entityToDto(employeeEntity);
        return ResponseEntity.ok(employeeDto);
    }

    // CRUD operasyonlarından okuma(read) işlemi
    // Verilen id'ye ait employee objesini return ediyor.
    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        // Verilen id'ye ait kayıt bulunamazsa Custom olarak oluşturduğum ResourceNotFoundException fırlatıyor
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not exist by given id : " + id));

        EmployeeDto employeeDto = entityToDto(employeeEntity);
        return ResponseEntity.ok(employeeDto);
    }

    // CRUD operasyonlarından güncelleme(update) işlemi.
    @Override
    public ResponseEntity<EmployeeDto> updateEmployeeById(Long id, EmployeeDto newEmployeeDto) {
        EmployeeEntity employeeEntity = dtoToEntity(newEmployeeDto);
        EmployeeEntity oldEmployee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not exist by given id " + id));

        // Username güncelleme
        oldEmployee.setUsername(employeeEntity.getUsername());

        // CompanyEntity güncelleme
        oldEmployee.setCompanyEntity(employeeEntity.getCompanyEntity());

        // Burada newEmployeeDto içerisinde yeni şifre var mı onun kontrolünü yapıyorum.
        // Eğer bu kontrolü yapmazsak aynı şifre 2.kez encode edilir ve şifrede istenmeyen bir değişiklik yapılmış olur.
        if(!passwordEncoder.matches(newEmployeeDto.getPassword(), oldEmployee.getPassword())){
            oldEmployee.setPassword(passwordEncoder.encode(employeeEntity.getPassword()));
        }

        // Database insert
        EmployeeEntity updatedCustomer = employeeRepository.save(oldEmployee);

        EmployeeDto employeeDto = entityToDto(updatedCustomer);
        return ResponseEntity.ok(employeeDto);
    }

    // CRUD operasyonlarından silme(delete) işlemi.
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not exist by given id " + id));

        //Database delete
        employeeRepository.delete(employeeEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    // Bu method verilen companyId ile o company'ye bağlı olan employee'leri return ediyor.
    @Override
    public List<EmployeeDto> getEmployeesOfCompany(Long companyId) {
        // Burada EmployeeRepository'nin içersinde custom olarak oluşturduğum methodu çağırıyorum.
        // Gerekli dönüşümleri yapıp return ediyorum.
        return employeeRepository.findEmployeeEntitiesByCompanyEntity(companyServices
                .dtoToEntity(companyServices.getCompanyById(companyId).getBody()))
                        .stream()
                        .map(this::entityToDto)
                        .toList();
    }

    // Entity object => dto object dönüşümü(Mapping).
    @Override
    public EmployeeDto entityToDto(EmployeeEntity customerEntity) {
        EmployeeDto employeeDto = modelMapper.map(customerEntity, EmployeeDto.class);
        employeeDto.setCompanyId(customerEntity.getCompanyEntity().getId());
        return employeeDto;
    }

    // Dto object => entity object dönüşümü(Mapping).
    @Override
    public EmployeeEntity dtoToEntity(EmployeeDto customerDto) {
        EmployeeEntity employeeEntity = modelMapper.map(customerDto, EmployeeEntity.class);
        employeeEntity.setCompanyEntity(companyServices.dtoToEntity(companyServices.getCompanyById(customerDto.getCompanyId()).getBody()));
        return employeeEntity;
    }
}
