package com.gcs4sqa.learningspringboot;

import com.gcs4sqa.learningspringboot.clientproxy.UserResourceV1;
import com.gcs4sqa.learningspringboot.model.User;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIT {

	@Autowired
	private UserResourceV1 userResourceV1;

	@Test

	public void shouldFetchAllUsers() {
		List<User> users = userResourceV1.fetchUsers(null);
		assertThat(users).hasSize(1);

		User joe = new User(null, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		assertThat(users.get(0)).isEqualToIgnoringGivenFields(joe,
				"userUid");
		assertThat(users.get(0).getUserUid()).isInstanceOf(UUID.class);
		assertThat(users.get(0).getUserUid()).isNotNull();
	}

	@Test
	public void shouldInsertUser() {
		//GIVEN
		UUID joeUserUid = UUID.randomUUID();
		User user = new User(joeUserUid, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		//WHEN
		userResourceV1.inserNewUser(user);

		//THEN
		User joe = userResourceV1.fetchUser(joeUserUid);
		assertThat(joe).isEqualToComparingFieldByField(user);
	}

	@Test
	public void shouldDeleteUsr() {

		//GIVEN
		UUID joeUserUid = UUID.randomUUID();
		User user = new User(joeUserUid, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		//WHEN
		userResourceV1.inserNewUser(user);

		//THEN
		User joe = userResourceV1.fetchUser(joeUserUid);
		assertThat(joe).isEqualToComparingFieldByField(user);

		//WHEN
		userResourceV1.deleteUser(joeUserUid);

		//THEN
		assertThatThrownBy(() -> userResourceV1.fetchUser(joeUserUid))
				.isInstanceOf(NotFoundException.class);


	}

	@Test
	public void shouldUpdateUser() {

		//GIVEN
		UUID joeUserUid = UUID.randomUUID();
		User user = new User(joeUserUid, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		//WHEN
		userResourceV1.inserNewUser(user);

		User updatedUser = new User(joeUserUid, "Alex", "Jones",
				User.Gender.MALE, 55, "alex.jones@gmail.com");

		userResourceV1.updateUser(updatedUser);

		//THEN
		user = userResourceV1.fetchUser(joeUserUid);
		assertThat(user).isEqualToComparingFieldByField(updatedUser);
	}

	@Test
	public void shouldGetchUsersByGender() {

		//GIVEN
		UUID joeUserUid = UUID.randomUUID();
		User user = new User(joeUserUid, "Joe", "Jones",
				User.Gender.MALE, 22, "joe.jones@gmail.com");

		//WHEN
		userResourceV1.inserNewUser(user);

		List<User> females = userResourceV1.fetchUsers(User.Gender.FEMALE.name());
		assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());

		List<User> males = userResourceV1.fetchUsers(User.Gender.MALE.name());
		assertThat(males).extracting("userUid").contains(user.getUserUid());
		assertThat(males).extracting("firstName").contains(user.getFirstName());
		assertThat(males).extracting("lastName").contains(user.getLastName());
		assertThat(males).extracting("gender").contains(user.getGender());
		assertThat(males).extracting("age").contains(user.getAge());
		assertThat(males).extracting("email").contains(user.getEmail());

	}
}
