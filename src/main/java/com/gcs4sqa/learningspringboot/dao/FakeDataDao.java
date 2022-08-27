package com.gcs4sqa.learningspringboot.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.gcs4sqa.learningspringboot.model.User;
import com.gcs4sqa.learningspringboot.model.User.Gender;

@Repository
public class FakeDataDao implements UserDao {

    private static Map<UUID, User> database;

    static {
        database = new HashMap<>();
        UUID userUid = UUID.randomUUID();
        database.put(userUid, 
        new User(userUid,
        "Joe",
        "Jones",
        Gender.Male,
        22, 
        "joe.jones@gmail.com"));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUid(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
    }

    @Override
    public int upDateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }

    @Override
    public int deleteUserByUserUid(UUID userUid) {
        database.remove(userUid);
        return 0;
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }
    
}
