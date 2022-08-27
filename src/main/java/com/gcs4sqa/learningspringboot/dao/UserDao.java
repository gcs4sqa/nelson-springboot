package com.gcs4sqa.learningspringboot.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gcs4sqa.learningspringboot.model.User;

public interface UserDao {

    List<User> selectAllUsers();

    Optional<User> selectUserByUserUid(UUID userUid);

    int upDateUser(User user);

    int deleteUserByUserUid(UUID userUid);

    int insertUser(UUID userUid,User user);


}
