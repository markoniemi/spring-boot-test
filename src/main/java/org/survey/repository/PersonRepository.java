package org.survey.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.survey.domain.Person;

@Repository
//@RestResource(exported=false)
public interface PersonRepository extends CrudRepository<Person, Long> {
}
