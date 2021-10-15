package project.bookrental.management;

import java.io.Serializable;
import java.text.*;
import java.util.*;
public class RentalTask implements Serializable{

	private String userid;		//회원 아이디
	private String bookid;		//도서 아이디
	private String rentalday;	//대여일자
	private String talvezReturnDay;  //반납예정일자
	private int arrears;			//연체료
	private UserDTO rtuser;		//회원객체
	private EachBookDTO ebdto;	//도서정보 객체 
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getRentalday() {	
		return rentalday;
	}

	public void setRentalday() {  //오늘로 설정
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		rentalday = date.format(new Date());
		this.rentalday = rentalday;
	}

	public String getTalvezReturnDay() {
		return talvezReturnDay;
	}

	public void setTalvezReturnDay() {		//대여일로부터 3일 뒤
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dRentalDay = sdf.parse(rentalday);
			
			Calendar cal = new GregorianCalendar(Locale.KOREA); //calendar타입 참조변수에 그래고리력 객체 생성해서 참조
			cal.setTime(dRentalDay);  			//반납예정일을 Calendar에 맵핑...???????????????
			cal.add(Calendar.DAY_OF_YEAR, 3);	//반납예정일로부터 3일을 더한다.
			
			this.talvezReturnDay = sdf.format(cal.getTime());	// 날짜를 문자열로 변경시키기
		} catch (ParseException e) {
			System.out.println("~~~ 반납예정일은 날자모양을 띄는 String 타입이어야 합니다.");
		}
	}

	public int getArrears() {
		return arrears;
	}

	public void setArrears() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String now = sdf.format(new Date());
			
			Date dayNow = sdf.parse(now);
			Date talvezReturnDate = sdf.parse(talvezReturnDay);
			
			long diff = dayNow.getTime()-talvezReturnDate.getTime();
			
			long diffDays = diff/(24*60*60*1000);
			
			if(diffDays >= 1) {
				arrears = ((int)diffDays)*100;
			}
			
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public UserDTO getRtuser() {
		return rtuser;
	}

	public void setRtuser(UserDTO rtuser) {
		this.rtuser = rtuser;
	}

	public EachBookDTO getEbdto() {
		return ebdto;
	}

	public void setEbdto(EachBookDTO ebdto) {
		this.ebdto = ebdto;
	}
	
	@Override
	public String toString() {
		String str = bookid +"\t"+
					ebdto.getIsbn()+"\t"+
					ebdto.getBdto().getBookName()+"\t"+
					ebdto.getBdto().getAuthor()+"\t"+
					ebdto.getBdto().getPublisher()+"\t"+
					userid+"\t"+
					rtuser.getUname()+"\t"+
					rtuser.getUtel()+"\t"+
					rentalday+"\t"+
					talvezReturnDay;
		return str;
	}
	
}
