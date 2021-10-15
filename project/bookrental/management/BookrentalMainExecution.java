package project.bookrental.management;

import java.util.Scanner;

public class BookrentalMainExecution {

	public static void main(String[] args) {

		InterBookrentalMngCtrl ctrl = new BookrentalMngCtrl();

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("\n===> 도서대여 프로그램 <===" + "\n1.사서 전용메뉴 2.일반회원 전용메뉴 3.프로그램 종료"
					+ "\n=> 메뉴번호선택 : ");
			String choose = sc.nextLine();
			switch (choose) {
			case "1":
				ctrl.showManagerMenu(sc);
				break;
			case "2":
				ctrl.showUserMenu(sc);
				break;
			case "3":
				System.exit(0);
				break;
			default:
				System.out.println("~~~~"+choose+" 는 존재하지 않는 메뉴입니다. 다시 선택해주세요.");
				break;
			}

		} // end of while------------------------
	}

}