package eu.glowacki.utp.assignment10.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import eu.glowacki.utp.assignment10.dtos.GroupDTO;

public class MyGroupRepository implements IGroupRepository{
	
	private Connection con;
	private String string;
	private PreparedStatement stmt;
	
	public MyGroupRepository(String JDBC_DRIVER, String DB_URL, String USER, String PASS) {
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			con.setAutoCommit(false);
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

	public List<GroupDTO> allGroupsInRepository() {
		List<GroupDTO> allGroupsInRepository = new LinkedList<>();
		string = "SELECT * FROM GROUPS";
		
		try {
			stmt = con.prepareStatement(string);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				allGroupsInRepository.add(new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return allGroupsInRepository;
	}
	
	@Override
	public void add(GroupDTO dto) {
		string = "INSERT INTO GROUPS VALUES (?, ?, ?)";
	
		try {
			stmt = con.prepareStatement(string);
			
			stmt.setInt(1, dto.getId());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getDescription());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 		
	}

	@Override
	public void update(GroupDTO dto) {
		string = "UPDATE GROUPS SET GROUP_NAME = ?, GROUP_DESCRIPTION = ? "
				+ "WHERE GROUP_ID = ?";
	
		try {
			stmt = con.prepareStatement(string);
			
			stmt.setString(1, dto.getName());
			stmt.setString(2, dto.getDescription());
			stmt.setInt(3, dto.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

	@Override
	public void addOrUpdate(GroupDTO dto) {
		if (exists(dto)) 
			update(dto);
		else
			add(dto);
	}

	@Override
	public void delete(GroupDTO dto) {
		
		string = "DELETE FROM GROUPS WHERE GROUP_ID = ? AND GROUP_NAME = ? AND GROUP_DESCRIPTION = ?";
	
		try {
			stmt = con.prepareStatement(string);
			stmt.setInt(1, dto.getId());	
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getDescription());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}

	@Override
	public GroupDTO findById(int id) {
		
		string = "SELECT * FROM GROUPS WHERE GROUP_ID = ?";
	
		try {
			stmt = con.prepareStatement(string);
			stmt.setInt(1, id);			

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				return new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void beginTransaction() {
//		try {
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
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
			//con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount(int id) {
		
		string = "SELECT COUNT(GROUP_ID) FROM GROUPS WHERE GROUP_ID = ?";
	
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
	public boolean exists(GroupDTO dto) {
		return getCount(dto.getId()) > 0;
	}

	@Override
	public List<GroupDTO> findByName(String name) {
		
		string = "SELECT * FROM GROUPS WHERE GROUP_NAME LIKE ?";
		List<GroupDTO> list = new LinkedList<>();
		
		try {
			stmt = con.prepareStatement(string);
			stmt.setString(1, "%" + name + "%");			

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(new GroupDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return list;
	}

}
