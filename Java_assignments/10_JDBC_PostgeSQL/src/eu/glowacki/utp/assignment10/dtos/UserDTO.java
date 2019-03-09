package eu.glowacki.utp.assignment10.dtos;

import java.util.LinkedList;
import java.util.List;

public class UserDTO extends DTOBase {

	private String _login;
	private String _password;
	private List<GroupDTO> _groups;

	public UserDTO() {}

	public UserDTO(int id, String login, String password) {
		super(id);
		_login = login;
		_password = password;
		_groups = new LinkedList<>();
	}
	
	public UserDTO(int id, String login, String password, List<GroupDTO> groups) {
		super(id);
		_login = login;
		_password = password;
		_groups = new LinkedList<>();
		_groups = groups;
	} 

	public String getLogin() {
		return _login;
	}

	public void setLogin(String login) {
		_login = login;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public List<GroupDTO> getGroups() {
		return _groups;
	}

	public void setGroups(List<GroupDTO> groups) {
		_groups = groups;
	}

	public void addGroup(GroupDTO group) {
		if (_groups == null) {
			_groups = new LinkedList<GroupDTO>();
		}
		_groups.add(group);
	}

	public void deleteGroup(GroupDTO group) {
		if (_groups != null) {
			_groups.remove(group);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass())
			return false;
		
		UserDTO other = (UserDTO) o;
		if (this.getId() == other.getId() && this._login.equals(other._login) 
				&& this._password.equals(other._password)) {
			if (_groups == null && other._groups == null)
				return true;
			else if (_groups != null && other._groups != null)
				return _groups.equals(other._groups);
			else
				return false;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return _login;
	}
}