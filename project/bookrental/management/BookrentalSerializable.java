package project.bookrental.management;

import java.io.*;

public class BookrentalSerializable {

	// 직렬화하는 메소드 생성하기(메모리상에 올라온 객체를 하드디스크 파일에 저장시키기)
	
	public int objectTolFileSave(Object obj, String saveFilename) {
		
		// === 객체 obj를 파일 saveFilename으로 저장하기 === //
		
		try {
			FileOutputStream fost = new FileOutputStream(saveFilename, false);
			//FileOutStream(String name(파일경로), boolean append(true-이어쓰기, false-덮어쓰기)
			// 출력노드 스트림(빨때꽂기) , 파일이 없으면 파일 생성도 해버림
			// 파일이름(saveFilename)을 이용해서 FileOutputStream 객체를 생성한다.
			// 생성된 객체는 두번째 파라미터 boolean append 값에 따라서 true이면 기존 파일에 내용을 덧붙여 추가, 
													//false이면 기존 내용은 삭제하고 새로운 내용을 추가함
			
			BufferedOutputStream bufOst = new BufferedOutputStream(fost, 1024);	//속도를 올리기 위한 bufferstream
			//필터스트림(노드 스트림의 속도향상을 위한 보조 스트림)
			
			ObjectOutputStream objOst = new ObjectOutputStream(bufOst);
			// objOst은 객체 obj를 파일명 saveFilename에 기록(저장)하기 위한 스트림 객체임
			
			objOst.writeObject(obj);
			// writeObject(); 객체 obj를 파일명 saveFilename에 기록하는 메소드
			
			objOst.close(); bufOst.close(); fost.close();
			// 사용된 자원 반납하기(사용된 객체를 메모리 공간에서 삭제하기)
			// 닫는 순서는 맨밑->위 순서임
			
			return 1;
			
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			return 0;
		} catch (IOException e) {
//			e.printStackTrace();
			return 0;
		} 
	}
	
	
	// 역직렬화하는 메소드 생성하기(하드디스크에 저장된 파일을 읽어다가 객체로 만들어 메모리에 올리게 하는 것)
	
	public Object getObjectFromFile(String fileName) {
			//=== 파일명 fileName을 읽어서 객체 Object로 변환하기 ===//
		
		try {
			FileInputStream finst = new FileInputStream(fileName);
			// 입력노드스트림(빨때꽂기)
			BufferedInputStream bufInst = new BufferedInputStream(finst, 1024);
			// 필터스트림(속도 up)
			
			ObjectInputStream objinst = new ObjectInputStream(bufInst);
			// 파일명 fileName을 읽어서 객체로 만들어주는 스트림 객체 생성
			
			Object obj = objinst.readObject();
			// 파일명 fileName을 읽어서 객체로 만들어주는 메소드
			
			objinst.close(); bufInst.close(); finst.close();
			// 사용된 자원 반납하기
		
			return obj;
			
		} catch (FileNotFoundException e) {
//			e.printStackTrace(); 		//오류메세지 콘솔창에 보여주지않기
		} catch (IOException e) {
//			e.printStackTrace();		
		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	
	
}
