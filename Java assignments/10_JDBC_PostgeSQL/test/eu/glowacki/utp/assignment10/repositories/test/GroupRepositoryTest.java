package eu.glowacki.utp.assignment10.repositories.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.glowacki.utp.assignment10.dtos.GroupDTO;
import eu.glowacki.utp.assignment10.repositories.IGroupRepository;
import eu.glowacki.utp.assignment10.repositories.MyGroupRepository;

public class GroupRepositoryTest extends RepositoryTestBase<GroupDTO, IGroupRepository> {	
	private MyGroupRepository _groupRepo;
	
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/UTP_assign_10";
	
	// Database credentials
	private static final String USER = "postgres";
	private static final String PASS = "admin";
	
	private final GroupDTO group11 = new GroupDTO(11, "group 11", "first group");
	private final GroupDTO group12 = new GroupDTO(12, "group 12", "second group");
	private final GroupDTO group13 = new GroupDTO(13, "group 13", "third group");
	
	private final GroupDTO updateGroup11 = new GroupDTO(11, "group 11 new", "update for group 11");
	private final GroupDTO updateGroup13 = new GroupDTO(13, "group 13 new", "update for group 13");
	
	@Test
	public void add() {
		_groupRepo.add(group11);
		_groupRepo.add(group12);
		
		Assert.assertEquals(Arrays.asList(group11, group12), _groupRepo.allGroupsInRepository());
	}

	@Test
	public void update() {
		_groupRepo.add(group11);
		_groupRepo.update(updateGroup11);
		
		Assert.assertEquals(Arrays.asList(updateGroup11), _groupRepo.allGroupsInRepository());
	}
	
	@Test
	public void addOrUpdate() {
		//add
		_groupRepo.addOrUpdate(group13);
		Assert.assertEquals(Arrays.asList(group13), _groupRepo.allGroupsInRepository());
		
		//update
		_groupRepo.addOrUpdate(updateGroup13);
		Assert.assertEquals(Arrays.asList(updateGroup13), _groupRepo.allGroupsInRepository());
	}

	@Test
	public void delete() {
		_groupRepo.add(group11);
		_groupRepo.add(group12);
		_groupRepo.add(group13);
		
		_groupRepo.delete(group12);
		
		Assert.assertEquals(Arrays.asList(group11, group13), _groupRepo.allGroupsInRepository());
	}

	@Test
	public void findById() {
		_groupRepo.add(group11);
		_groupRepo.add(group12);
		_groupRepo.add(group13);
		
		GroupDTO found = _groupRepo.findById(11);
		Assert.assertEquals(group11, found);
	}
	
	@Test
	public void findByName() {
		_groupRepo.add(group11);
		_groupRepo.add(group12);
		_groupRepo.add(group13);
		
		List<GroupDTO> found = _groupRepo.findByName("group");
		
		Assert.assertEquals(Arrays.asList(group11, group12, group13), found);
	}

	@Override
	protected IGroupRepository Create() {
		_groupRepo = new MyGroupRepository(JDBC_DRIVER, DB_URL, USER, PASS);
		return _groupRepo;
	}
}