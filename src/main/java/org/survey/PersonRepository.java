package org.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(exported=false)
public interface PersonRepository extends CrudRepository<Person, Long> {
}
