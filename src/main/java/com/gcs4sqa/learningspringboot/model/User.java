package com.gcs4sqa.learningspringboot.model;

import java.util.UUID;

public class User {

    // User id
    private UUID userUid;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String email;

    public User(UUID userUid, String firstName, String lastName, Gender gender, Integer age, String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public User() {
    }

    public void setUserUid(UUID uuid) {
        this.userUid = uuid;

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
        MALE,
        FEMALE
    }
}
