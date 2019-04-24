package edu.uh.tech.cis3368.exam2;

import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor,Integer> {
    Professor findByLastName(String name);
}
