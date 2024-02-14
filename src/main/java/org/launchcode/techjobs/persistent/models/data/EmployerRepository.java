package org.launchcode.techjobs.persistent.models.data;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This maps the Employer class to the techjobs database
//it is a data access interface, and utilizes CrudRepository interface to map the objects
@Repository
public interface EmployerRepository extends CrudRepository<Employer, Integer> {
}
