package Entities;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament {
    private int id;
    private String name;

    public Medicament(){

    }

    public Medicament(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
