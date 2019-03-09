package eu.glowacki.utp.assignment10.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import eu.glowacki.utp.assignment10.dtos.GroupDTO;
import eu.glowacki.utp.assignment10.dtos.UserDTO;

public class MyUserRepository implements IUserRepository{

	private Connection con;
	private String string;
	private PreparedStatement stmt;
	
	public MyUserRepository(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.err.println("Cannot load Driver");
			System.out.println(e);
		} catch (SQLException e) {
			System.err.println("Connection cannot be established");
			System.out.println(e);
		}
		string = null;
		stmt = null;
	}
	
	@Override
	public Connection getConnection() {
		return con;
	}

	public List<UserDTO> allUsersInRepository() {
		List<UserDTO> allUsersInRepository = new LinkedList<>();
		string = "SELECT * FROM USERS";
		
		try {
			stmt = con.prepareStatement(string);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				allUsersInRepository.add(new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return allUsersInRepository;
	}
	
	@Override
	public void add(UserDTO dto) {
		string = "INSERT INTO USERS VALUES (?, ?, ?)";
	
		try {
			stmt = con.prepareStatement(string);
			
			stmt.setInt(1, dto.getId());
			stmt.setString(2, dto.getLogin());
			stmt.setString(3, dto.getPassword());
			
			stmt.executeUpdate();	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 		
	}

	@Override
	public void update(UserDTO dto) {
		string = "UPDATE USERS SET USER_LOGIN = ?, USER_PASSWORD = ? "
				+ "WHERE STUDENT_ID = ?";
	
		try {
			stmt = con.prepareStatement(string);
			
			stmt.setString(1, dto.getLogin());
			stmt.setString(2, dto.getPassword());
			stmt.setInt(3, dto.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

	@Override
	public void addOrUpdate(UserDTO dto) {
		if (exists(dto)) 
			update(dto);
		else
			add(dto);
	}

	@Override
	public void delete(UserDTO dto) {
		
		string = "DELETE FROM USERS WHERE STUDENT_ID = ? AND USER_LOGIN = ? AND USER_PASSWORD = ?";
	
		try {
			stmt = con.prepareStatement(string);
			stmt.setInt(1, dto.getId());	
			stmt.setString(2, dto.getLogin());
			stmt.setString(3, dto.getPassword());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

	@Override
	public UserDTO findById(int id) {
		
		string = "SELECT * FROM USERS WHERE STUDENT_ID = ?";
		String string2 = "SELECT GROUP_ID, GROUP_NAME, GROUP_DESCRIPTION "
				+ "FROM GROUPS JOIN GROUPS_USERS ON GROUPS.GROUP_ID = GROUPS_USERS.GROUP_ID, "
				+ "USERS JOIN GROUPS_USERS ON USERS.STUDENT_ID = GROUPS_USERS.STUDENT_ID "
				+ "WHERE STUDENT_ID = ?";
		try {
			stmt = con.prepareStatement(string);
			stmt.setInt(1, id);			
			
			PreparedStatement stmt2 = con.prepareStatement(string2);
			stmt2.setInt(1, id);		
			
			ResultSet rs = stmt.executeQuery();
			
			ResultSet groups = stmt2.executeQuery();
			List<GroupDTO> list = null;
			while(groups.next()) {
				if (list == null) {
					list = new LinkedList<>();
				}
				list.add(new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			
			while(rs.next()) {
				return new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), list);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void beginTransaction() {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void commitTransaction() {
		try {
			con.commit();
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollbackTransaction() {
		try {
			con.rollback();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount(int id) {
		
		string = "SELECT COUNT(*) FROM USERS WHERE STUDENT_ID = ?";
	
		try {
			stmt = con.prepareStatement(string);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean exists(UserDTO dto) {
		return getCount(dto.getId()) > 0;
	}

	@Override
	public List<UserDTO> findByName(String name) {
		
		string = "SELECT * FROM USERS WHERE USER_LOGIN LIKE ?";
		List<UserDTO> list = new LinkedList<>();
		
		try {
			stmt = con.prepareStatement(string);
			stmt.setString(1, "%" + name + "%");			

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return list;
	}

}
