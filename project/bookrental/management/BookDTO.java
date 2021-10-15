package project.bookrental.management;

import java.io.Serializable;

public class BookDTO implements Serializable{
	
	private static final long serialVersionUID = 5944330762204113338L;
	private String ISBN;
	private String category;
	private String bookName;
	private String author;
	private String publisher;
	private int price;
//	private UserDTO userDTO;
	
	public BookDTO() {	}
	public BookDTO(String iSBN, String category, String bookName, String author, String publisher, int price) {
		ISBN = iSBN;
		this.category = category;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
//		this.userDTO = userDTO;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
//	public UserDTO getUserDTO() {
//		return userDTO;
//	}
//	public void setUserDTO(UserDTO userDTO) {
//		this.userDTO = userDTO;
//	}
	
	
}
