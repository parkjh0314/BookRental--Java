package project.bookrental.management;

import java.io.File;
import java.lang.reflect.Array;
import java.rmi.server.SocketSecurityException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookrentalMngCtrl implements InterBookrentalMngCtrl {

	private final String BOOKLIST = "D:/지현/001 프로그래밍 공부/쌍용강북/JavaProject/src/project/bookrental/booklist.dat"; // "C:/iotestdata/project/bookrental/booklist.dat";
	private final String BOOKIDLIST = "D:/지현/001 프로그래밍 공부/쌍용강북/JavaProject/src/project/bookrental/bookidlist.dat";// "C:/iotestdata/project/bookrental/bookidlist.dat";
	private final String USERLIST = "D:/지현/001 프로그래밍 공부/쌍용강북/JavaProject/src/project/bookrental/userlist.dat";// "C:/iotestdata/project/bookrental/userlist.dat";
	private final String MANAGERLIST = "D:/지현/001 프로그래밍 공부/쌍용강북/JavaProject/src/project/bookrental/managerlist.dat";// "C:/iotestdata/project/bookrental/managerlist.dat";
	private final String RENTALTASK = "D:/지현/001 프로그래밍 공부/쌍용강북/JavaProject/src/project/bookrental/rentallist.dat";// "C:/iotestdata/project/bookrental/rentallist.dat";

	private BookrentalSerializable serial = new BookrentalSerializable();

//=============================================================================================================================
	@Override
	public void showManagerMenu(Scanner sc) {

		String smenu = "1.사서가입 2.로그인 3.로그아웃 4.도서정보등록 5.개별도서등록 6.도서대여해주기 7.대여중인도서조회 8.도서반납해주기 9.나가기" + "\n=> 메뉴번호선택 : ";
		String choose = "";
		String name = null;
		while (!choose.equals("9")) {
			if (name == null) {
				System.out.print("\n>>>> 사서 전용 메뉴 <<<<");
				System.out.print("\n" + smenu);
			} else if (name != null) { // name변수에 값이 있다는 것 => 로그인을 했다는 것
				System.out.println("\n>>>> 사서 전용 메뉴 [" + name + " 로그인중...]<<<<");
				System.out.print(smenu);
			}
			choose = sc.nextLine();
			switch (choose) {
			case "1":
				managerSignUp(sc);
				break; // while문 처음으로
			case "2":
				if (name == null) { // name값이 없을 때
					name = managerlogin(sc); // 로그인 메소드 실행, 리턴값을 name 변수에 넣어주기
					break;
				} else if (name != null) { // name변수에 값이 있을 때
					System.out.println("이미[" + name + "님]이 로그인 되어 있습니다.");
					break;
				}
			case "3":
				name = null;
				System.out.println("=== 로그아웃 되었습니다. ===");
				break;
			case "4":
				if (permit(name)) {
					registerBook(sc);
				}
				break;
			case "5":
				if (permit(name)) {
					addEachBook(sc);
				}
				break;
			case "6":
				if (permit(name)) {
					rentBook(sc);
				}
				break;
			case "7":
				if (permit(name)) {
					showRentBook();
				}
				break;
			case "8":
				if (permit(name)) {
					retrunBook(sc);
				}
				break;
			case "9":
				break;
			default:
				System.out.println(">>>>위 메뉴중에서 선택해주세요.<<<<");
				break;
			}
		} // end of while------------------------------------------------------
	}

//=============================================================================================================================
	@Override
	public void showUserMenu(Scanner sc) {

		String umenu = "1.일반회원가입 2.로그인 3.로그아웃 4.도서검색하기 5.나의대여현황보기 6.나가기" + "\n=> 메뉴번호선택 : ";
		String name = null;
		String choose = "";

		while (!choose.equals("6")) { // 6을 선택하지 않았다면 반복실행
			if (name == null) {
				System.out.print("\n>>>> 일반회원 전용 Menu <<<<");
				System.out.print("\n" + umenu);
			} else if (name != null) {
				System.out.println("\n>>>> 일반회원 전용 Menu [" + name + " 로그인중..] <<<<");
				System.out.print(umenu);
			}
			choose = sc.nextLine();
			switch (choose) {
			case "1":
				userSignUp(sc);
				break;
			case "2":
				if (name == null) { // name값이 없을 때
					name = userlogin(sc); // 로그인 메소드 실행, 리턴값을 name 변수에 넣어주기
					break;
				} else if (name != null) { // name변수에 값이 있을 때
					System.out.println("이미[" + name + "님]이 로그인 되어 있습니다.");
					break;
				}
			case "3":
				name = null;
				System.out.println("=== 로그아웃 되었습니다. ===");
				break;
			case "4":
				searchBook(sc);
				break;
			case "5":
				showMyState(name);
				break;
			case "6":
				break;
			}
		} // end of while-------------------------------------------------------------
	}

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	// >>>>>>>>>>>>>사서회원가입<<<<<<<<<<
	public void managerSignUp(Scanner sc) {
		String sid = null, spwd = null;
		System.out.println("\n== 사서가입하기 ==");
		while (true) {
			sid = checkStr("사서ID", sc);
			if (managerCheckId(sid)) {
				System.out.println("~~~~" + sid + " 는 이미 존재하므로 다른 사서 ID를 입력해주세요!");
			} else {
				break;
			}
		}

		spwd = checkStr(" 암호", sc);

		ManagerDTO mngdto = new ManagerDTO(sid, spwd);

		File file = new File(MANAGERLIST); // 해당경로에 managerlist 파일 생성

		List<ManagerDTO> mnglist = null;

		int n = 0;

		if (!file.exists()) { // mnglist파일이 존재하지 않는 경우(=최초 사서 아이디 등록)
			mnglist = new ArrayList<>();
			mnglist.add(mngdto);
		} else { // mnglist파일이 존재하는 경우 : 저장된 ArrayList객체를 볼러와서 새로 생성된 객체를 추가
			mnglist = (ArrayList<ManagerDTO>) serial.getObjectFromFile(MANAGERLIST);
			mnglist.add(mngdto);
		}

		n = serial.objectTolFileSave(mnglist, MANAGERLIST); // MANAGERLIST경로에 mngList 객체를 저장한다.

		if (n == 1) {
			System.out.println(">>> 사서등록 성공!! <<<");
		} else {
			System.out.println(">>> 사서등록 실패!! <<<");
		}
	}

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public boolean managerCheckId(String id) {

		boolean usando = false;

		// 비교할 객체가 있는 파일을 불러옴
		List<ManagerDTO> mnglist = (ArrayList<ManagerDTO>) serial.getObjectFromFile(MANAGERLIST);

		if (mnglist != null) { // mngMap이 null값이면 최초 등록이므로 중복검사 필요X
			for (ManagerDTO list : mnglist) {
				if (list.getSid().equals(id)) {
					usando = true; // 입력받은 id와 일치하는 값이 있으면 usando를 true로 줌
				}
			}
		}
		return usando; // true-중복됨(사용불가) / false-중복안됨(사용가능)
	}

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public void registerBook(Scanner sc) {

		String ISBN = null;
		System.out.println("\n== 도서정보 등록하기 ==");
		boolean a = true;

		while (a) {
			ISBN = checkStr("국제표준도서번호(ISBN)", sc);
			if (checkIsbn(ISBN)) {
				System.out.println("~~~~" + ISBN + "은 이미 존재하므로 다른 국제표준도서번호(ISBN)를 입력하세요!!");
			} else {
				break;
			}
		}

		String category = checkStr("도서분류카테고리", sc);
		String bookName = checkStr("도서명", sc);
		String author = checkStr("작가명", sc);
		String publisher = checkStr("출판사", sc);
		int price = checkNum("▶가격", sc);

		BookDTO bookdto = new BookDTO(ISBN, category, bookName, author, publisher, price);

		File file = new File(BOOKLIST); // 해당경로에 booklist 파일 생성

		List<BookDTO> booklist = null;

		int n = 0;

		if (!file.exists()) { // booklist파일이 존재하지 않는 경우(=최초 도서정보 등록)
			booklist = new ArrayList<>(); // List<BookDTO>타입 booklist참조변수에 ArrayList<BookDTO>객체를 생성하여 추가인덱스에 참조
			booklist.add(bookdto);
		} else { // mnglist파일이 존재하는 경우 : 저장된 ArrayList객체를 볼러와서 새로 생성된 객체를 추가
			booklist = (ArrayList<BookDTO>) serial.getObjectFromFile(BOOKLIST);
			booklist.add(bookdto);
		}

		n = serial.objectTolFileSave(booklist, BOOKLIST); // BOOKLIST경로에 booklist 객체를 저장한다.

		if (n == 1) {
			System.out.println(">>> 도서정보등록 성공!! <<<");
		} else {
			System.out.println(">>> 도서정보등록 실패!! <<<");
		}

	}

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public void addEachBook(Scanner sc) {

		String bookid = null;

		System.out.print("\n== 개별도서 등록하기 ==\n");

		// 국제표준 도서번호를 받는다.
		String isbn = checkStr("국제표준도서번호(ISBN)", sc);

		// 현재 도서관의 booklist에 일치하는 국제표준도서번호가 있는지 확인한다.
		if (!checkIsbn(isbn)) { // 등록되있지 않은 경우
			System.out.println(">>> 등록된 ISBN이 아닙니다. 도서등록 실패!! <<<");
		} else { // 등록되어있는 경우 도서아이디를 받는다.
			while (true) {
				bookid = checkStr("도서아이디", sc);
				if (checkBookId(bookid)) { // 도서아이디가 중복인 경우 (=등록불가능)
					System.out.println("~~~" + bookid + " 는 이미 존재하므로 다른 도서아이디를 입력하세요!!");
				} else { // 도서아이디 등록가능한 경우
					break;
				}
			}

			BookDTO bdto = null; // 입력받은 isbn과 동일한 isbn 필드를 가지고 있는 BookDTO를 받을 변수 선언

			List<BookDTO> blist = (ArrayList<BookDTO>) serial.getObjectFromFile(BOOKLIST); // BookDTO가 있는 파일 불러오기
			for (BookDTO igual : blist) {
				if (igual.getISBN().equals(isbn)) { // for문 돌려서 입력받은 isbn과 동일한 isbn 필드를 가지고 있는 BookDTO 찾기
					bdto = igual; // 선언한 변수에 찾은 BookDTO 참조하기
				}
			}

			EachBookDTO ebdto = new EachBookDTO(isbn, bookid, bdto); // EachBookDTO 객체 만들기

			File file = new File(BOOKIDLIST); // EachBookDTOList 파일 만들어주기

			List<EachBookDTO> bookidlist = null;

			int n = 0;
			// 파일 유무 확인후 불러와서 Arraylist추가
			if (!file.exists()) { // bookidlist 파일이 존재하지 않는 경우(=개별도서 최초등록)
				bookidlist = new ArrayList<>(); // 파일에 저장할 ArrayList객체 생성
				bookidlist.add(ebdto);
			} else { // bookidlist파일이 존재하는 경우
				bookidlist = (ArrayList<EachBookDTO>) serial.getObjectFromFile(BOOKIDLIST);
				bookidlist.add(ebdto);
			}

			n = serial.objectTolFileSave(bookidlist, BOOKIDLIST);

			if (n == 1) {
				System.out.println(">>> 도서등록 성공!! <<<");
			} else {
				System.out.println(">>> 도서등록 실패!! <<<");
			}
		}
	}// end of method------------------

//=============================================================================================================================
	@Override
	public void rentBook(Scanner sc) {
		List<UserDTO> udto = (ArrayList<UserDTO>) serial.getObjectFromFile(USERLIST);

		System.out.println("\n>>> 도서대여하기 <<<");
		
		String uid = null;
		boolean flag1 = true;
		
		while (flag1) {
			System.out.print("▶회원ID : ");
			uid = sc.nextLine();
			for (UserDTO userdto : udto) {
				if (uid.trim().isEmpty() || uid == null || !(userdto.getUid().equals(uid))) { // 입력된uid값이 공백이거나
					System.out.println("~~~등록된 회원ID가 아닙니다~~~");
					break;
				} else if (userdto.getUid().equals(uid)) {
					flag1 = false;
					break;
				}
			} // end of for------------------------------------------------------
		} // end of while--------------------------------------------------------

		File file = new File(RENTALTASK); // 파일생성
		List<RentalTask> rtlist = null;

		if (!file.exists()) { // 파일이 존재X -> 첫대여
			rtlist = new ArrayList<RentalTask>();
		} else { // 파일이 존재O
			rtlist = (ArrayList<RentalTask>) serial.getObjectFromFile(RENTALTASK);

			// 대여하려는 회원에게 연체된도서가 있는지 확인 -> 있다:대여불가 / 없다:대여가능
			for (RentalTask rt : rtlist) {
				if (rt.getUserid().equals(uid)) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String dayNow = sdf.format(new Date());

						Date dateNow = sdf.parse(dayNow);
						Date talvezRtDate = sdf.parse(rt.getTalvezReturnDay());

						long diff = dateNow.getTime() - talvezRtDate.getTime();

						if (diff > 0) {
							System.out.println("~~~~ 반납예정일을 넘긴 미반납된 도서가 존재하므로 도서대여가 불가능합니다.!!!!");
							return;
						}

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} // end of for-------------------------------------------------
		} // end of if~else-------------------------------------------------

		//
		List<EachBookDTO> eblist = (ArrayList<EachBookDTO>) serial.getObjectFromFile(BOOKIDLIST);

		// 총대여권수 입력
		int rtTotaln = 0;
		while (true) {
			System.out.print("▶ 총대여권수 : ");
			String rtTotal = sc.nextLine();

			try {
				rtTotaln = Integer.parseInt(rtTotal);
				if (rtTotaln < 1) {
					System.out.println("~~~ 총대여권수는 1권 이상이어야 합니다. ~~~");
					continue;
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("~~~ 숫자로만 입력하세요 ~~~");
			}
		} // end of while------------------------------------------

		// 대여해갈 도서ID 입력
		for (int i = 0; i < rtTotaln; i++) {
			String bid = null;
			while (true) {
				bid = checkStr("도서ID", sc);
				if (!checkBookId(bid)) { // 없는 도서ID이면
					System.out.println("~~~ 존재하지 않는 도서ID 입니다. 다시 입력하세요!! ~~~");
				} else {
					break;
				}
			} // end of while --------------

			// 대여중인 도서ID입력시 대여불가 안내
			boolean flag = false;
			for (EachBookDTO ebook : eblist) {
				if (ebook.getBookid().equals(bid) && !ebook.isBookState()) {
					System.out.println("~~~ 현재 대여중인 도서ID입니다. 새로운 도서ID를 입력하세요!");
					flag = true;
					break;
				}
			} // end of for------------
			if (flag) { // 총대여횟수에서 대여중인 도서ID검색으로 횟수가 한번 소비됐으니 다시 채워주기
				i--;
				continue;
			}

			RentalTask rt = new RentalTask(); // 대여 객체 생성
			rt.setUserid(uid);
			rt.setBookid(bid);
			rt.setRentalday();
			rt.setTalvezReturnDay();

			rtlist.add(rt);

			// 개별도서객체에서 대여가능여부를 false(대여중)로 변경
			for (int j = 0; j < eblist.size(); j++) {
				if (eblist.get(j).getBookid().equals(bid)) {
					eblist.get(j).setBookState(false);
					break;
				}
			} // end of for--------------------------

		} // end of for---------------------------

		int n = serial.objectTolFileSave(rtlist, RENTALTASK);

		if (n == 1) {
			System.out.println(">>> 대여등록 성공!! <<<");
			int m = serial.objectTolFileSave(eblist, BOOKIDLIST);
			if (m == 1) {
				System.out.println(">>> 대여도서 비치중으로 변경완료 <<<");
			}
		} else {
			System.out.println(">>> 대여등록 실패!! <<<");
		}

	}// end of method--------------------------------------------------

//=============================================================================================================================
	@Override
	public void showRentBook() {
		System.out.println("================================================================================================================================================="
				+ "\nISBN\t\t\t도서아이디\t\t\t도서명\t\t작가명\t출판사\t회원ID\t회원명\t연락처\t\t대여일자\t\t반납예정일"
				+ "\n=================================================================================================================================================");
		List<RentalTask> rtlist = (ArrayList<RentalTask>) serial.getObjectFromFile(RENTALTASK);
		String str = null;
		for (RentalTask rt : rtlist) {
			if (!rt.getEbdto().isBookState()) {
				str = rt.getEbdto().getIsbn() + "/t" + rt.getBookid() + "/t" + rt.getEbdto().getBdto().getBookName()
						+ "/t" + rt.getEbdto().getBdto().getAuthor() + "/t" + rt.getEbdto().getBdto().getPublisher()
						+ "/t" + rt.getUserid() + "/t" + rt.getRtuser().getUname() + "/t" + rt.getRtuser().getUtel()
						+ "/t" + rt.getRentalday() + "/t" + rt.getTalvezReturnDay();
				System.out.println(str);
			} else {
				System.out.println("~~~~~ 대여정보가 없습니다.~~~~~");
			}
		} // end of for

	}// end of method

//=============================================================================================================================
	@Override
	public void retrunBook(Scanner sc) {

		List<RentalTask> rtlist = (ArrayList<RentalTask>) serial.getObjectFromFile(RENTALTASK);
		List<EachBookDTO> eblist = (ArrayList<EachBookDTO>) serial.getObjectFromFile(BOOKIDLIST);
		System.out.println(">>> 도서반납하기 <<<");
		int nrtBook = 0;
		while (true) {
			nrtBook = checkNum("총반납권수", sc);
			if (nrtBook < 1) {
				System.out.println("~~~ 총반납권수는 1권 이상이어야 합니다. ~~~");
				continue;
			} else {
				break;
			}
		}

		// 반납 도서ID입력
		int sumArrears = 0; // 연체료 합계 변수
		for (int i = 0; i < nrtBook; i++) {
			String bid = null;
			while (true) {
				bid = checkStr("반납도서ID", sc);

				if (!checkBookId(bid)) {
					System.out.println("~~~ 존재하지 않는 도서ID입니다. 다시 입력하세요!! ~~~");
				} else {
					break;
				}
			}

			// 대여목록에서 삭제
			for (int j = 0; j < rtlist.size(); j++) {
				if (rtlist.get(j).getBookid().equals(bid)) {
					// 연체료 계산
					rtlist.get(j).setArrears();
					System.out.print("도서별 연체료 : " + rtlist.get(j).getArrears());
					sumArrears += rtlist.get(j).getArrears();

					rtlist.remove(j); // 대여목록에서 삭제
					break;
				}
			}

			// 개별도서 객체에서 BookState를 true(비치중)으로 변경
			for (int j = 0; j < eblist.size(); j++) {
				if (eblist.get(j).getBookid().equals(bid)) {
					eblist.get(j).setBookState(true);
					break;
				}
			} // end of for----------------------------------------
		} // end of for-------------------------------------------------

		int n = serial.objectTolFileSave(rtlist, RENTALTASK);

		if (n == 1) {
			System.out.println(">>> 도서반납 성공!! <<<");
			int m = serial.objectTolFileSave(eblist, BOOKIDLIST);
			if (m == 1) {
				System.out.println(">>> 대여도서 대여중에서 비치중으로 변경완료 <<<");
			}

			DecimalFormat df = new DecimalFormat("#,###");
			System.out.printf("▶연체료 총계 : %,d원\n", sumArrears);
		} else {
			System.out.println(">>> 도서반납 실패!! <<<");
		}

	}// end of method---------------------------------------------------------------------------------------------

//=============================================================================================================================
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void searchBook(Scanner sc) {

		// 받은 문자열과 비교할 BookDTOlist불러오기
		List<EachBookDTO> eblist = (ArrayList<EachBookDTO>) serial.getObjectFromFile(BOOKIDLIST);

		// 출력문 헤드
		String head = "================================================================================================================================================="
				+ "\nISBN\t\t\t도서아이디\t\t\t도서명\t\t작가명\t출판사\t가격\t대여상태"
				+ "\n=================================================================================================================================================";

		// 받은문자열==null이면 전체출력
		// 받은문자열!=null이면 bookDTO에서 비교해서 출력
		// 받은문자열!=null 두개 이상이면 겹치는 것만 출력
		// 검색어 대소문자 구분X
		// 반든문자열!=null인데 없는 거면 출력X

		System.out.print("\n▶ 도서분류카테고리(Programming, DataBase 등) : ");
		String bookCategory = sc.nextLine();
		System.out.print("▶ 도서명 : ");
		String bname = sc.nextLine();
		System.out.print("▶ 작가명 : ");
		String aname = sc.nextLine();
		System.out.print("▶ 출판사명 : ");
		String pname = sc.nextLine();

		ArrayList<EachBookDTO> book_category = new ArrayList<>();
		if (!nohay(bookCategory)) { // 입력된 값이 있으면 bookDTO에 입력받은 값과 같은 필드값을 가진 bookDTO만 ArrayList에 추가
			for (EachBookDTO a : eblist) {
				if (a.getBdto().getCategory().equalsIgnoreCase(bookCategory)) {
					book_category.add(a);
				}
			}
		} else if (nohay(bookCategory)) { // 입력된 값이 없으면 모든 bookDTO를 ArrayList에 추가
			for (EachBookDTO a : eblist) {
				book_category.add(a);
			}
		}

		ArrayList<EachBookDTO> book_name = new ArrayList<>();
		if (!nohay(bname)) {
			for (EachBookDTO a : eblist) {
				if (a.getBdto().getBookName().equalsIgnoreCase(bname)) {
					book_name.add(a);
				}
			}
		} else if (nohay(bname)) {
			for (EachBookDTO a : eblist) {
				book_name.add(a);
			}
		}

		ArrayList<EachBookDTO> book_author = new ArrayList<>();
		if (!nohay(aname)) {
			for (EachBookDTO a : eblist) {
				if (a.getBdto().getAuthor().equalsIgnoreCase(aname)) {
					book_author.add(a);
				}
			}
		} else if (nohay(aname)) {
			for (EachBookDTO a : eblist) {
				book_author.add(a);
			}
		}

		ArrayList<EachBookDTO> book_publisher = new ArrayList<>();
		if (!nohay(pname)) {
			for (EachBookDTO a : eblist) {
				if (a.getBdto().getPublisher().equalsIgnoreCase(pname)) {
					book_publisher.add(a);
				}
			}
		} else if (nohay(pname)) {
			for (EachBookDTO a : eblist) {
				book_publisher.add(a);
			}
		}

		// 4개의 ArrayList에서 같은 동일한 bookDTO를 찾아 출력하기

		ArrayList<EachBookDTO> s1 = new ArrayList<>();
		ArrayList<EachBookDTO> s2 = new ArrayList<>();
		ArrayList<EachBookDTO> s3 = new ArrayList<>();

		for (int i = 0; i < book_category.size(); i++) {
			for (int j = 0; j < book_name.size(); j++) {
				if (book_category.get(i).getBdto().equals(book_name.get(j).getBdto())) { // book_category ArrayList에 있는
																							// bookDTO와 book_name
																							// ArrayList에 있는 bookDTO중
																							// 일치하는 bookDTO가 있는지 확인
					s1.add(book_name.get(j)); // 일치하는 bookDTO가 있으면 s1 ArrayList에 추가
				}
			}
		}

		for (int i = 0; i < s1.size(); i++) {
			for (int j = 0; j < book_author.size(); j++) {
				if (s1.get(i).getBdto().equals(book_author.get(j).getBdto())) {
					s2.add(book_author.get(j));
				}
			}
		}

		for (int i = 0; i < s2.size(); i++) {
			for (int j = 0; j < book_publisher.size(); j++) {
				if (s2.get(i).getBdto().equals(book_publisher.get(j).getBdto())) {
					s3.add(book_publisher.get(j));
				}
			}
		}

		System.out.println(head);
		if (s3.size() == 0) {
			System.out.println("~~~~ 검색에 일치하는 도서가 없습니다. ~~~~");
		} else if (s3.size() != 0) {
			for (EachBookDTO a : s3) {
				System.out.println(a.getIsbn() + "\t" + a.getBookid() + "\t" + a.getBdto().getBookName() + "\t\t"
						+ a.getBdto().getAuthor() + "\t" + a.getBdto().getPublisher() + "\t" + a.getBdto().getPrice()
						+ "\t" + a.toString());
			}
		}

	}// end of
		// method--------------------------------------------------------------------

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	// >>>>>>>>유저회원가입<<<<<<<<<<<<<<<<
	public void userSignUp(Scanner sc) {
		String uid = null, upwd = null, uname = null, utel = null;
		System.out.println("\n== 일반회원 가입하기 ==");
		while (true) {
			uid = checkStr("회원ID", sc);
			if (UserCheckId(uid)) {
				System.out.println("~~~~" + uid + " 는 이미 존재하므로 다른 회원ID를 입력해주세요!");
			} else {
				break;
			}
		}

		upwd = checkStr("암호", sc);
		uname = checkStr("성명", sc);
		utel = checkStr("연락처", sc);

		UserDTO userdto = new UserDTO(uid, upwd, uname, utel);

		File file = new File(USERLIST); // 해당경로에 userlist 파일 생성

		List<UserDTO> userlist = null;

		int n = 0;

		if (!file.exists()) { // mnglist파일이 존재하지 않는 경우(=최초 사서 아이디 등록)
			userlist = new ArrayList<>();
			userlist.add(userdto);
		} else { // userlist파일이 존재하는 경우 : 저장된 ArrayList객체를 볼러와서 새로 생성된 객체를 추가
			userlist = (List<UserDTO>) serial.getObjectFromFile(USERLIST);
			userlist.add(userdto);
		}

		n = serial.objectTolFileSave(userlist, USERLIST); // USERLIST경로에 ArrayList 객체를 저장한다.

		if (n == 1) {
			System.out.println(">>> 회원등록 성공!! <<<");
		} else {
			System.out.println(">>> 회원등록 실패!! <<<");
		}
	}

//=============================================================================================================================
	@SuppressWarnings("unchecked")
	@Override
	public boolean UserCheckId(String uid) {

		boolean usando = false;

		// 비교할 객체가 있는 파일을 불러옴
		List<UserDTO> userlist = (List<UserDTO>) serial.getObjectFromFile(USERLIST);

		if (userlist != null) { // userlist가 null값이면 최초 등록이므로 중복검사 필요X
			for (UserDTO list : userlist) {
				if (list.getUid().equals(uid)) {
					usando = true;
				}
			} // end of for------------------------------------------------
		}
		return usando; // true-중복됨(사용불가) / false-중복안됨(사용가능)
	}

//=============================================================================================================================
	@Override
	public void showMyState(String uid) {

		List<RentalTask> rtlist = (ArrayList<RentalTask>)serial.getObjectFromFile(RENTALTASK);
		List<EachBookDTO> eblist = (ArrayList<EachBookDTO>)serial.getObjectFromFile(BOOKIDLIST);
		
		if(rtlist != null) {
			System.out.println("================================================================================================================================================="
					+ "\n도서ID\t\t\tISBN\t\t\t도서명\t\t작가명\t출판사\t회원ID\t대여일자\t\t반납예정일"
					+ "\n=================================================================================================================================================");
			boolean flag = false;
			for(RentalTask rt : rtlist) {
				if(rt.getUserid().equals(uid)) {
					flag = true;
					
					int index = rt.getBookid().lastIndexOf("-");
					String isbn = rt.getBookid().substring(0, index);
					EachBookDTO ebdto = null;
					for(EachBookDTO eb : eblist) {
						if(eb.getIsbn().equals(isbn)) {
							ebdto = eb;
						}
					}
					rt.setEbdto(ebdto); 	//대여업무객체에 개별도서객체 정보 입력하기
					
					System.out.println(rt.getBookid()+"\t"+
							rt.getEbdto().getIsbn()+"\t"+
							rt.getEbdto().getBdto().getBookName()+"\t"+
							rt.getEbdto().getBdto().getAuthor()+"\t"+
							rt.getEbdto().getBdto().getPublisher()+"\t"+
							uid+"\t"+
							rt.getRentalday()+"\t"+
							rt.getTalvezReturnDay());
				}
			}// end of for----------------------------------------------------
			
			if(!flag) {
				System.out.println("~~~ 대여해가신 도서가 없습니다. ~~~");
			}
		}else{
			System.out.println("~~~ 대여정보가 없습니다. ~~~");
		}
		
	}//end of method---------------------------------------------------
//=============================================================================================================================

	@Override
	public String checkStr(String str, Scanner sc) {
		String s = null;
		do {
			System.out.print("▶" + str + ": ");
			s = sc.nextLine();
			if (s.trim().isEmpty()) {
				System.out.println(str + "를 입력하세요!!!");
			} else {
				break;
			}
		} while (true);
		return s;
	}

//==============================================================================================================================
	@Override
	public int checkNum(String str, Scanner sc) {
		String s = null;
		int n = 0;
		do {
			System.out.print(str + ": ");
			s = sc.nextLine();
			if (s.trim().isEmpty()) {
				System.out.println(str + "를 입력하세요!!!");
			} else {
				try {
					n = Integer.parseInt(s);
					break;
				} catch (Exception e) {
					System.out.println("~~~ 오류 : " + str + "은 숫자로만 입력하세요!!!");
				}
			}
		} while (true);
		return n;
	}

//==================================================================================================================================
	@Override
	public boolean checkIsbn(String isbn) {

		// 국제표준번호 유효성 검사하기
		// 입력받은 값이 이미 등록된 ISBN이면 true 아니면 false값 반환

		List<BookDTO> booklist = (ArrayList<BookDTO>) serial.getObjectFromFile(BOOKLIST);
		if (booklist == null) {
			return false;
		} else {
			for (BookDTO list : booklist) {
				if (list.getISBN().equals(isbn)) { // booklist에 입력받은 ISBN과 일치하는 ISBN필드값을 가진 BookDTO값이 있는지 확인
					return true;
				}
			}
		}
		return false;
	}

//=====================================================================================================================================
	public boolean permit(String name) {
		if (name == null) {
			System.out.println(">>>권한이 없습니다. 로그인 후 접속 가능합니다.<<<");
			return false;
		} else {
			return true;
		}
	}// 로그인 됐을 때 true, 로그인 안 됐으면 false 반환
//======================================================================================================================================

	@SuppressWarnings("unchecked")
	public boolean checkBookId(String bookid) {

		// Bookid값이 겹치는지 확인 //겹치면 true값을 반환, 겹치지 않으면 false값을 반환

		List<EachBookDTO> eblist = (ArrayList<EachBookDTO>) serial.getObjectFromFile(BOOKIDLIST);
		if (eblist == null) {
			return false;
		} else {
			for (EachBookDTO ebdto : eblist) {
				if (ebdto.getBookid().equals(bookid))
					return true;
			}
		}
		return false;
	}

//========================================================================================================================================

	@Override
	public String managerlogin(Scanner sc) {
		System.out.println("\n=== 사서로그인하기 ===");

		// 존재하는 데이터인지 확인

		String id = checkStr("ID", sc);
		String pwd = checkStr("암호", sc);

		List<ManagerDTO> mnglist = (ArrayList<ManagerDTO>) serial.getObjectFromFile(MANAGERLIST);

		for (ManagerDTO mng : mnglist) {
			if (mng.getSid().equals(id) && mng.getSpwd().equals(pwd)) {
				System.out.println(">>> 로그인 성공!!<<<");
				return mng.getSid();
			}
		}

		System.out.println("\n>>> 로그인 실패 !!! <<<");
		return null;
	}

//========================================================================================================================================

	@Override
	public String userlogin(Scanner sc) {
		System.out.println("\n=== 유저로그인하기 ===");

		// 존재하는 데이터인지 확인

		String id = checkStr("ID", sc);
		String pwd = checkStr("암호", sc);

		List<UserDTO> userlist = (ArrayList<UserDTO>) serial.getObjectFromFile(USERLIST);

		for (UserDTO user : userlist) {
			if (user.getUid().equals(id) && user.getUpwd().equals(pwd)) {
				System.out.println(">>> 로그인 성공!!<<<");
				return user.getUname();
			}
		}

		System.out.println("\n>>> 로그인 실패 !!! <<<");
		return null;
	}

	// =============================================================================================================================
	public boolean nohay(String algo) {
		if (algo == null || algo.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}// end of
	// class----------------------------------------------------------------------------------------------