package com.gcs4sqa.learningspringboot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;

import com.gcs4sqa.learningspringboot.dao.FakeDataDao;
import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.model.User.Gender;

import com.google.common.collect.ImmutableList;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("all")
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
         "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");
        
         ImmutableList<User> users = new ImmutableList.Builder<User>()
         .add(anna).build();

        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);
        assertUserFields(user);
    }

    

    @Test
    void testGetUser() {
        UUID annUuid = UUID.randomUUID();
        User anna = new User(annUuid, "Anna",
        "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annUuid)).willReturn(Optional.of(anna));

        Optional<User> userOptional = userService.getUser(annUuid);
        
        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();
        assertUserFields(user);

    }


  

    @Test
    void testInsertUser() {
        User anna = new User(null, "Anna",
        "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");

        given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class); 

        int insertResult = userService.insertUser(anna);
        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
        User user = captor.getValue();

        assertUserFields(user);
        assertThat(insertResult).isEqualTo(1);


    }

    @Test
    void testRemoveUser() {
        UUID annUuid = UUID.randomUUID();
        User anna = new User(annUuid, "Anna",
        "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annUuid)).willReturn(Optional.of(anna));
        given(fakeDataDao.deleteUserByUserUid(annUuid)).willReturn(1);


        int deleteResult = userService.removeUser(annUuid);

        verify(fakeDataDao).selectUserByUserUid(annUuid);
        verify(fakeDataDao).deleteUserByUserUid((annUuid));

        assertThat(deleteResult).isEqualTo(1);


    }

    @Test
    void testUpDateUser() {
        UUID annUuid = UUID.randomUUID();
        User anna = new User(annUuid, "Anna",
        "montana", Gender.FEMALE, 20, "anna.montana@gmail.com");

        given(fakeDataDao.selectUserByUserUid(annUuid)).willReturn(Optional.of(anna));
        given(fakeDataDao.upDateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class); 

        int updateResult = userService.upDateUser(anna);

        verify(fakeDataDao).selectUserByUserUid(annUuid);
        verify(fakeDataDao).upDateUser(captor.capture());

        User user = captor.getValue();
        assertUserFields(user);
        assertThat(updateResult).isEqualTo(1);





    }

    private void assertUserFields(User user){
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna.montana@gmail.com");
        assertThat(user.getUserUid()).isNotEqualTo(null);
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);

    }
}
