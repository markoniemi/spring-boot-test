package org.survey.service;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.survey.domain.Person;

@RestController
@Path("/people")
public class PeopleRestService {
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @RequestMapping("people")
    public Collection<Person> getPeople() {
        return Collections.singletonList(new Person("a@b.com", "John", "Smith"));
    }
}