package org.survey;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/persons")
public class PersonRestService {
    @Resource
    private PersonRepository personRepository;
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    @RequestMapping("persons")
    public List<Person> getPeople() {
        return IterableUtils.toList(personRepository.findAll());
    }
}
