package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {
    @NotBlank
    @Size(min=3, max=50, message="Value must be between 3 and 50 characters long.")
    private String location;

    @OneToMany
    @JoinColumn (name = "employer_id")
    private final List<Job> jobs = new ArrayList<>();
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employer(String aLocation) {
        super();
        this.location = aLocation;
    }

    public Employer() {}
}
