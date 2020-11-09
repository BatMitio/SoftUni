package entities.hospital;

import entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private LocalDate dateOfBirth;
    private byte[] picture;
    private boolean isMedicalInsuranceProvided;
    private Set<Visitation> visitations;
    private Set<Diagnose> diagnoses;
    private Set<Medicament> prescribedMedicaments;

    @Column(name = "first_name", length = 50, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 50, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "address", length = 100, nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "date_of_birth", nullable = false)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "picture")
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Column(name = "is_medical_insurance_provided")
    public boolean isMedicalInsuranceProvided() {
        return isMedicalInsuranceProvided;
    }

    public void setMedicalInsuranceProvided(boolean medicalInsuranceProvided) {
        isMedicalInsuranceProvided = medicalInsuranceProvided;
    }

    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class,
    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    @OneToMany(mappedBy = "patient", targetEntity = Diagnose.class,
    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    @ManyToMany(mappedBy = "patients", targetEntity = Medicament.class,
    fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Medicament> getPrescribedMedicaments() {
        return prescribedMedicaments;
    }

    public void setPrescribedMedicaments(Set<Medicament> prescribedMedicaments) {
        this.prescribedMedicaments = prescribedMedicaments;
    }
}
