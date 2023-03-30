package rserenity.enoca.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "companies")
public class CompanyEntity{
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name",nullable = false, unique = true)
    private String companyName;

    @JsonIgnore
    @OneToMany(targetEntity = EmployeeEntity.class)
    private Set<EmployeeEntity> employees= new HashSet<>();

    public CompanyEntity(String companyName, Set<EmployeeEntity> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }
}