package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    //TODO#1: What is the purpose of this index method?
    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    //This passes data from the database into the "add" view so that the user can select
    // from the existing employer and skill options when adding a new job
    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        //TODO#2: What does the following line of code do exactly?
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());

        return "add";
    }

    //TODO#3 This takes the user input from the "Add Job" page and saves it in MySQL, ya?
    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model,
                                    @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional<Employer> result = employerRepository.findById(employerId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Employer ID: " + employerId);
            } else {
                Employer employer = result.get();
                model.addAttribute("title", "Jobs from employer: " + employer.getName());
                newJob.setEmployer(employer);
            }
            List<Skill> skillList = (List<Skill>) skillRepository.findAllById(skills);

            newJob.setSkills(skillList);
            jobRepository.save(newJob);
            return "redirect:/list/jobs?column=All&value=View All";
        }

//This takes the jobId path variable, searches the database for the matching job object,
// and returns the view template for the given jobId
    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

//TODO#4 If I am using findById, do I always need to use Optional<>?
        Optional<Job> result = jobRepository.findById(jobId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Job ID: " + jobId);
        } else {
            Job job = result.get();
            model.addAttribute("title", "Job with ID: " + jobId);
            model.addAttribute("job", job);
        }

        return "view";
    }
    //TODO#5 Why is this particular collection of methods what gets put into
    // the Controller called HomeController? Why do they belong together?
    // For the EmployerController there is a separate folder for the employers templates.
    // Same for the SkillController. But not for this one. Why?
}
