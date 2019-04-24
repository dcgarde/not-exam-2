package edu.uh.tech.cis3368.exam2;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ResearchInterestRepository extends CrudRepository<ResearchInterest,Integer> {
    ArrayList<ResearchInterest> findAllByProfessorId(int id);

}
