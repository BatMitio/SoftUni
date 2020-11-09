package Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visits")
public class Visit {
    private int id;
    private Patient patient;
    private Diagnose diagnose;
    private Medicament medicament;
    private Date visitDate;
    private List<VisitComment> comments;

    public Visit(){

    }

    public Visit(Patient patient, Diagnose diagnoses, Medicament medicament){
        this.patient = patient;
        this.diagnose = diagnoses;
        this.medicament = medicament;
        this.comments = new ArrayList<VisitComment>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Patient.class, cascade = CascadeType.ALL)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    @Column(name = "visit_date")
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }


    @OneToMany(mappedBy = "visit")
    public List<VisitComment> getComments() {
        return comments;
    }

    public void setComments(List<VisitComment> comments) {
        this.comments = comments;
    }
}
