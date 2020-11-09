package Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    private int id;
    private String name;
    private List<DiagnoseComment> comments;
    private List<Visit> visits;


    public Diagnose(){

    }

    public Diagnose(String name){
        this.name = name;
        this.visits = new ArrayList<Visit>();
        this.comments = new ArrayList<DiagnoseComment>();
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

    @OneToMany(mappedBy = "diagnose")
    public List<DiagnoseComment> getComments() {
        return comments;
    }

    public void setComments(List<DiagnoseComment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "diagnose")
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
