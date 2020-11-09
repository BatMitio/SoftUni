package entities.bills_payment_system;

import entities.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "users")
public class user extends BaseEntity {


    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Set<basic_billing_details> billing_details;


    @Column(name = "first_name", nullable = false)
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "password",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @OneToMany
    public Set<basic_billing_details> getBilling_details() {
        return billing_details;
    }

    public void setBilling_details(Set<basic_billing_details> billing_details) {
        this.billing_details = billing_details;
    }

    public user() {
    }

}
