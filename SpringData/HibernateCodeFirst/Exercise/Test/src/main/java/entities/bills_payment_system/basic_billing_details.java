package entities.bills_payment_system;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class basic_billing_details   {
    private String number;
    private user owner;


    @Id
    @Column(unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @ManyToOne
    public user getOwner() {
        return this.owner;
    }

    public void setOwner(user owner) {
        this.owner = owner;
    }
}
