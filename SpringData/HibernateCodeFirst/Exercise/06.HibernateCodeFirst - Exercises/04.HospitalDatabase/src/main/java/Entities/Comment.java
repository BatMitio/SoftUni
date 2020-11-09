package Entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Comment {
    private int id;
    private String text;

    public Comment(){}


    public Comment(String text){
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
