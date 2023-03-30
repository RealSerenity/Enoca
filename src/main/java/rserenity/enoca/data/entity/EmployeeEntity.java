package rserenity.enoca.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@NoArgsConstructor
@Log4j2
@Data
@Entity
@Table(name = "users")
public class EmployeeEntity{
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;


    public EmployeeEntity(String username, String password, CompanyEntity companyEntity) {
        this.username = username;
        this.password = password;
        this.companyEntity = companyEntity;
    }
}