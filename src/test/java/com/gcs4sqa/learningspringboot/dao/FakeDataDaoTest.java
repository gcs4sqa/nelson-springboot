package com.gcs4sqa.learningspringboot.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.model.User.Gender;

@SuppressWarnings("all")
public class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @BeforeEach
    public void setup(){
        fakeDataDao = new FakeDataDao();
        
    }

   

    @Test
    void testSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);
        User user = users.get(0);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getFirstName()).isEqualTo("Joe");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("joe.jones@gmail.com");
        assertThat(user.getUserUid()).isNotEqualTo(null);




    }

    @Test
    void testSelectUserByUserUid() {
        UUID annaUuid = UUID.randomUUID();
        User anna = new User(annaUuid, "Anna",
         "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");
         fakeDataDao.insertUser(annaUuid, anna);
         assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
         Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaUuid);
         assertThat(annaOptional.isPresent()).isTrue();
         assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);


    }

    @Test
    void testNotSelectUserByUserUid() {
        Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(user.isPresent()).isFalse();
        


    }

    @Test
    void testUpDateUser() {

        UUID joeUserUuid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newJoe = new User(joeUserUuid, "Anna",
         "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");
         fakeDataDao.upDateUser(newJoe);
         Optional<User> user = fakeDataDao.selectUserByUserUid(joeUserUuid);
         assertThat(user.isPresent()).isTrue();
         assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
         assertThat(user.get()).isEqualToComparingFieldByField(newJoe);

    }

    @Test
    void testDeleteUserByUserUid() {
        UUID joeUserUuid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserByUserUid(joeUserUuid);
        assertThat(fakeDataDao.selectUserByUserUid(joeUserUuid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();

    }
    @Test
    void testInsertUser() {
        UUID userUuid = UUID.randomUUID();
        User user = new User(userUuid, "Anna",
         "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");
        fakeDataDao.insertUser(userUuid, user);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        Optional<User> userOptional = fakeDataDao.selectUserByUserUid(userUuid);
         assertThat(userOptional.isPresent()).isTrue();
         assertThat(userOptional.get()).isEqualToComparingFieldByField(user);


    }
}
