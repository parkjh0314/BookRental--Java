package project.bookrental.management;

import java.io.Serializable;

public class EachBookDTO implements Serializable { // serializable 인터페이스 상속 안하면 직렬화불가

	// 클래스 이름에 노란줄뜨는 곳에 커서 대면 나오는 거 위에서 두번째줄 누르기(add generate serial version ID)
	private static final long serialVersionUID = -5262915944342166856L;

	private String isbn;
	private String bookid;
	private BookDTO bdto;
	private boolean bookState = true;

	public EachBookDTO() {
	}

	public EachBookDTO(String isbn, String bookid, BookDTO bdto) {
		this.isbn = isbn;
		this.bookid = bookid;
		this.bdto = bdto;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}


	public BookDTO getBdto() {
		return bdto;
	}

	public void setBdto(BookDTO bdto) {
		this.bdto = bdto;
	}

	public boolean isBookState() {
		return bookState;
	}

	public void setBookState(boolean bookState) {
		this.bookState = bookState;
	}

	@Override
	public String toString() {
		if(bookState) {
			return "비치중";
		}else {
			return "대여중";
		}
	}

}
