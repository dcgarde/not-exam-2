package edu.uh.tech.cis3368.exam2;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ResearchInterest implements Serializable {
    private int id;

    private String name;
    private Professor professor;

    public ResearchInterest() {
    }

    public ResearchInterest(String newInterestName) {
        name = newInterestName;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchInterest)) return false;
        ResearchInterest that = (ResearchInterest) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, professor);
    }
}
