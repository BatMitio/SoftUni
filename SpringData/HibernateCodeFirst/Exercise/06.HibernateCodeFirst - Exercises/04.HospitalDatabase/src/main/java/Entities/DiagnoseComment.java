package Entities;

import javax.persistence.*;

@Entity
@Table(name = "diagnose_comments")
public class DiagnoseComment extends Comment{

    private Diagnose diagnose;

    public DiagnoseComment(){

    }

    public DiagnoseComment(String text) {
        super(text);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
