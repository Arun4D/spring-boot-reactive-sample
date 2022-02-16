package in.co.ad.springboot.reactiveservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table("employee")
public class Employee {
    @Id
    @Column("id")
    private long id;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

    @Column("date_of_birth")
    private String dob;
}
