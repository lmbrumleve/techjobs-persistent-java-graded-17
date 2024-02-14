package org.launchcode.techjobs.persistent.models.data;

import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This maps the Skill class to the techjobs database
//it is a data access interface, and utilizes CrudRepository interface to map the objects

@Repository
public interface SkillRepository extends CrudRepository <Skill, Integer> {
}
