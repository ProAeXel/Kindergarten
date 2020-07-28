package com.grigorescu.kindergarten;

public class DemoEducator {

    private String firstname, lastname, description, group, email;

    public DemoEducator(String firstname, String lastname, String description, String group, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
        this.group = group;
        this.email = email;
    }

    public DemoEducator()
    {}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
