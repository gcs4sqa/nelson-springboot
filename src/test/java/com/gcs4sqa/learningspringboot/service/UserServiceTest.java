package com.gcs4sqa.learningspringboot.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;


import static org.mockito.BDDMockito.given;

import com.gcs4sqa.learningspringboot.dao.FakeDataDao;
import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.model.User.Gender;

import com.google.common.collect.ImmutableList;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest<Users> {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);

    }

    @Test
    void testGetAllUsers() {
        UUID annaUuid = UUID.randomUUID();
        User anna = new User(annaUuid, "Anna",
         "montana", Gender.Female, 20, "anna.montana@gmail.com");
        
         ImmutableList<User> users = new ImmutableList.Builder<User>()
         .add(anna).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers();

        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(Gender.Female);
        assertThat(user.getEmail()).isEqualTo("anna.montana@gmail.com");
        assertThat(user.getUserUid()).isNotEqualTo(null);
    }

    

    @Test
    void testGetUser() {

    }

    @Test
    void testInsertUser() {

    }

    @Test
    void testRemoveUser() {

    }

    @Test
    void testUpDateUser() {

    }
}
