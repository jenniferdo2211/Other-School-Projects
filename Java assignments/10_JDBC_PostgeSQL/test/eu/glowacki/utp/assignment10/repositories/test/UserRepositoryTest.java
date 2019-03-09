package eu.glowacki.utp.assignment10.repositories.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.glowacki.utp.assignment10.dtos.UserDTO;
import eu.glowacki.utp.assignment10.repositories.IUserRepository;
import eu.glowacki.utp.assignment10.repositories.MyUserRepository;

public final class UserRepositoryTest extends RepositoryTestBase<UserDTO, IUserRepository> {
	private MyUserRepository _userRepo;
	
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/UTP_assign_10";
	
	// Database credentials
	private static final String USER = "postgres";
	private static final String PASS = "admin";

	private final UserDTO user1 = new UserDTO(1001, "user 1", "first user");
	private final UserDTO user2 = new UserDTO(1002, "user 2", "second user");
	private final UserDTO user3 = new UserDTO(1003, "user 3", "third user");
	
	private final UserDTO updateUser1 = new UserDTO(1001, "user 1 new", "update for user 1");
	private final UserDTO updateUser3 = new UserDTO(1003, "user 3 new", "update for user 3");
	
	@Test
	public void add() {
		_userRepo.add(user1);
		_userRepo.add(user2);
		
		Assert.assertEquals(Arrays.asList(user1, user2), _userRepo.allUsersInRepository());
	}

	@Test
	public void update() {
		_userRepo.add(user1);
		_userRepo.update(updateUser1);
		
		Assert.assertEquals(Arrays.asList(updateUser1), _userRepo.allUsersInRepository());
	}
	
	@Test
	public void addOrUpdate() {
		//add
		_userRepo.addOrUpdate(user3);
		Assert.assertEquals(Arrays.asList(user3), _userRepo.allUsersInRepository());
		
		//update
		_userRepo.addOrUpdate(updateUser3);
		Assert.assertEquals(Arrays.asList(updateUser3), _userRepo.allUsersInRepository());
	}

	@Test
	public void delete() {
		_userRepo.add(user1);
		_userRepo.add(user2);
		_userRepo.add(user3);
		
		_userRepo.delete(user2);
		
		Assert.assertEquals(Arrays.asList(user1, user3), _userRepo.allUsersInRepository());
	}

	@Test
	public void findById() {
		_userRepo.add(user1);
		_userRepo.add(user2);
		_userRepo.add(user3);
		
		UserDTO found = _userRepo.findById(1001);
		Assert.assertEquals(user1, found);
	}
	
	@Test
	public void findByName() {
		_userRepo.add(user1);
		_userRepo.add(user2);
		_userRepo.add(user3);
		
		List<UserDTO> found = _userRepo.findByName("user");
		
		Assert.assertEquals(Arrays.asList(user1, user2, user3), found);
	}
	
	@Override
	protected IUserRepository Create() {
		_userRepo = new MyUserRepository(JDBC_DRIVER, DB_URL, USER, PASS);
		return _userRepo;
	}
}