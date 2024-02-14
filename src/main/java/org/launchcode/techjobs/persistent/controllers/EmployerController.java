package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    //lists all employers in the view
    //TODO: This renders a view at localhost:8080/employers/ but there is not a link to navigate to it
    @GetMapping("/")
    public String index(Model model){
//        model.addAttribute("title", "All Employers");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        //TODO: Sort of like a question I had in the HomeController, but what is this line of code doing?
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

//sends the form back if any of the submitted employer object information is invalid
        if (errors.hasErrors()) {
            return "employers/add";
        }
//TODO: This saves a new Employer object in the MySQL database, ya?
        employerRepository.save(newEmployer);

        return "redirect:/";
    }

    //renders a page to view the contents of an individual employer object
    //grabs correct info from employerRepository
    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        //Here, optional will look for the employer in employer repository based on the id
// TODO: Is this way of using Optional preferable to the way I did it in HomeController? Why?
        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }

    }
}
