package org.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//@RepositoryRestResource(path="/persons")
public interface PersonRepository extends CrudRepository<Person, Long> {

}