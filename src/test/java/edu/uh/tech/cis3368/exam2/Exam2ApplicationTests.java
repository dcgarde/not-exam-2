package edu.uh.tech.cis3368.exam2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // causes each test to clean up after itself
public class Exam2ApplicationTests {

    final String PROF1 = "Frederick";

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ResearchInterestRepository researchInterestRepository;

    @Test
    public void a_testCreatingAProfessor(){
        Professor professor = new Professor();
        professor.setLastName("Detillier");
        professor.setOffice("325 T2");
        professorRepository.save(professor);
        // should be only 1 in the database
        assertEquals(1,professorRepository.count());
    }


    @Test
    public void b_testAProfCanHaveInterests(){
        Professor myProfessor = new Professor();
        myProfessor.setLastName(PROF1);
        myProfessor.setOffice("1234 T1");

        // add a research interest
        ResearchInterest researchInterest = new ResearchInterest();
        researchInterest.setName("Machine Learning");
        researchInterest.setProfessor(myProfessor);
        myProfessor.addResearchInterest(researchInterest);
        researchInterestRepository.save(researchInterest);
        assertEquals("the professor should have one interest",1, myProfessor.getResearchInterests().size());

    }

    @Test
    public void c_testAnInterestCanBeRemoved(){

        Professor myProfessor = new Professor();
        myProfessor.setLastName(PROF1);
        myProfessor.setOffice("123");

        // add an interest
        ResearchInterest researchInterest = new ResearchInterest();
        researchInterest.setName("Machine Learning");
        researchInterest.setProfessor(myProfessor);
        myProfessor.addResearchInterest(researchInterest);
        researchInterestRepository.save(researchInterest);

        // add a second interest
        ResearchInterest researchInterest2 = new ResearchInterest();
        researchInterest.setName("OOAD");
        researchInterest.setProfessor(myProfessor);
        myProfessor.addResearchInterest(researchInterest2);
        researchInterestRepository.save(researchInterest2);

        // remove one interest
        Professor professorReloaded = professorRepository.findByLastName(PROF1);
        professorReloaded.removeResearchInterest(researchInterest2);
        professorRepository.save(professorReloaded);

        // load from db just to make sure
        Professor professorReloadedAgain = professorRepository.findByLastName(PROF1);
        assertNotNull("professor not found", professorReloadedAgain);
        assertEquals("professor should have one interest, not two",1, professorReloadedAgain.getResearchInterests().size());
    }

    @Test
    public void d_testAProfWithInterestsCanHaveInterestsRemoved(){
        Professor myProfessor = new Professor();
        myProfessor.setLastName(PROF1);
        myProfessor.setOffice("123 T1");

        ResearchInterest researchInterest = new ResearchInterest();
        researchInterest.setName("Machine Learning");
        researchInterest.setProfessor(myProfessor);
        myProfessor.addResearchInterest(researchInterest);
        researchInterestRepository.save(researchInterest);


        ResearchInterest researchInterest2 = new ResearchInterest();
        researchInterest.setName("Software Engineering");
        researchInterest.setProfessor(myProfessor);
        myProfessor.addResearchInterest(researchInterest2);
        researchInterestRepository.save(researchInterest2);

        // check number of interests
        assertEquals(2, myProfessor.getResearchInterests().size());
        myProfessor.dropInterests();
        professorRepository.save(myProfessor);
        assertEquals("the professor should have no interests",0, myProfessor.getResearchInterests().size());

        // insure that no fleas are assigned to the dog
        assertEquals("there should be no interests tied to the prof",
                0, researchInterestRepository.findAllByProfessorId(myProfessor.getId()).size());
    }



}
