package project.bookrental.management;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = -9034860520881564710L;
	private String uid;
	private String upwd;
	private String uname;
	private String utel;
//	private BookDTO bookDTO;

	public UserDTO() {	}

	public UserDTO(String uid, String upwd, String uname, String utel) {
		this.uid = uid;
		this.upwd = upwd;
		this.uname = uname;
		this.utel = utel;
//		this.bookDTO = bookDTO;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUtel() {
		return utel;
	}

	public void setUtel(String utel) {
		this.utel = utel;
	}

//	public BookDTO getBookDTO() {
//		return bookDTO;
//	}
//
//	public void setBookDTO(BookDTO bookDTO) {
//		this.bookDTO = bookDTO;
//	}
	
	
	
}