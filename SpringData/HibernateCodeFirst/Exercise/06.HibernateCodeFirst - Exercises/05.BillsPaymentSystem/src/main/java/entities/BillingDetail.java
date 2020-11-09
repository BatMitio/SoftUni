package entities;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
public class BillingDetail extends BaseEntity {

    private String billingDetail;
    private User owner;

    public BillingDetail() {
    }

    @Column(name = "billing_detail")
    public String getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(String billingDetail) {
        this.billingDetail = billingDetail;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
