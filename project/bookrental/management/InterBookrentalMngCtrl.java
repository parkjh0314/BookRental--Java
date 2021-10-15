package project.bookrental.management;

import java.util.Scanner;

public interface InterBookrentalMngCtrl {

	void showManagerMenu(Scanner sc);	//사서 메뉴 보여주기
	void managerSignUp(Scanner sc);		//사서 회원가입
	boolean managerCheckId(String sid);	//사서 아이디 중복확인
	
	String managerlogin(Scanner sc);			//사서로그인
	String userlogin(Scanner sc);				//유저로그인
	
	void registerBook(Scanner sc);		//도서정보 등록
	boolean checkIsbn(String isbn);		// isbn중복검사
	void addEachBook(Scanner sc);		//개별도서 등록
	void rentBook(Scanner sc);			//도서 대여
	void showRentBook();				//대여중인 도서 조회
	void retrunBook(Scanner sc);		//도서 반납
	void searchBook(Scanner sc);		//도서 검색
	
	void showUserMenu(Scanner sc);		//일반 회원 메뉴
	void userSignUp(Scanner sc);		//회원가입
	boolean UserCheckId(String uid);	//유저아이디 중복확인
	void showMyState(String uid);		//나의 대여현황 보기
	
	String checkStr(String str, Scanner sc);	//공백 검사
	int checkNum(String str, Scanner sc);		//공백 검사
	
}