package edu.uh.tech.cis3368.exam2;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Professor {

    private int id;
    private String lastName;
    private String firstName;
    private String office;


    private Set<ResearchInterest> researchInterests = new HashSet<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    public Set<ResearchInterest> getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(Set<ResearchInterest> researchInterests) {
        this.researchInterests = researchInterests;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "OFFICE")
    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", office='" + office + '\'' +
                '}';
    }

    /**
     * Adds interest to the research
     * */
    public void addResearchInterest(ResearchInterest researchInterest) {
        researchInterests.add(researchInterest);

    }


    /**
     * Removes interest from a professor
     * */
    public void removeResearchInterest(ResearchInterest researchInterest) {
        Iterator<ResearchInterest> iterator = researchInterests.iterator();
        while(iterator.hasNext()){
            ResearchInterest interest = iterator.next();
            if(interest.equals(researchInterest)){
                iterator.remove();
            }
        }


    }

    /**
     * Drops all interest from a professor
     * */
    public void dropInterests() {
        researchInterests.clear();


    }
}
