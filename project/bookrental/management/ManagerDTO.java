package project.bookrental.management;

import java.io.Serializable;

public class ManagerDTO implements Serializable{

	private static final long serialVersionUID = 8469589810304027617L;
	
	private String sid;
	private String spwd;
	
	public ManagerDTO() {	}

	public ManagerDTO(String sid, String spwd) {
		this.sid = sid;
		this.spwd = spwd;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSpwd() {
		return spwd;
	}

	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}

	
}
