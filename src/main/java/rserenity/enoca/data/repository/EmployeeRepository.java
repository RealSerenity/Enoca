package rserenity.enoca.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rserenity.enoca.data.entity.CompanyEntity;
import rserenity.enoca.data.entity.EmployeeEntity;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findEmployeeEntitiesByCompanyEntity(CompanyEntity companyEntity);
}
