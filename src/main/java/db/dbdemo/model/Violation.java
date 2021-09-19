package db.dbdemo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "violations")
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private long tax;
    @OneToMany(mappedBy = "violation")
    private Set<ViolationsLog> violations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public Set<ViolationsLog> getViolations() {
        return violations;
    }

    public void setViolations(Set<ViolationsLog> violations) {
        this.violations = violations;
    }
}
