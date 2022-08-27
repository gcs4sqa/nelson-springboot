package com.gcs4sqa.learningspringboot.model;

import java.util.UUID;

public class User {

    //User id
    private final UUID userUid;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final Integer age;
    private final String email;


    public User(UUID userUid, String firstName, String lastName, Gender gender, Integer age, String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }


    public UUID getUserUid() {
        return this.userUid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "{" +
            " userUid='" + getUserUid() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", age='" + getAge() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }



    public enum Gender {
        Male,
        Female
    }
}
