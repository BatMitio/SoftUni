package Entities;

import javax.persistence.*;

@Entity
@Table(name = "visit_comments")
public class VisitComment extends Comment{

    private Visit visit;

    public VisitComment(){

    }

    public VisitComment(String text) {
        super(text);
    }

    @ManyToOne
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
}
