package edu.kh.comm.member.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.comm.commom.Util;
import edu.kh.comm.member.model.dao.MyPageDAO;
import edu.kh.comm.member.model.vo.Member;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private MyPageDAO dao;
	
	//비밀번호 변경을 위해서
	//암호화를 위한bcrypt 객체 의존성 주입(DI)
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	//회원 정보 수정 서비스 구현
	@Override
	public int updateInfo(Map<String, Object> paramMap) {
		
		return dao.updateInfo(paramMap);
	}

	//비밀번호 변경 서비스 구현
	//암호화 써줘야함 (위에!)
	@Override
	public int changePw(Map<String, Object> paramMap) {
		
		
		//1)  DB에서 현재 회원의 비밀번호를 조회
		//	-> 회원 비밀번호는 db에만 있기 대문에 db에서 꺼내와야함
		String encPw = dao.selectEncPw((int)paramMap.get("memberNo"));
//		dao -> mapper 다녀옴
		
		//2) 입력된 현재 비밀번호 (평문상태)와 
		// 	조회된 비밀번호(암호화된 상태)를 비교한다.(bcrypt.matches() 이용)
		
		
		
		//3) 비교한 결과가 일치하면
		// 새로운 비밀번호를 암호화해서 update 구문 수행
		if(bcrypt.matches((String)paramMap.get("currentPw"), encPw)) {
			
			paramMap.put("newPw", bcrypt.encode((String)paramMap.get("newPw")));
			//Map에 이미 같은 key가 존재하면
			//value만 덮어씌
			//새로운 비밀번호를 map에 담아 비크립트를 거쳐 보냄
			return dao.changePw(paramMap);
			
		}
		//비교 결과가 일치하지 않으면 0반환
		//-> "현재 비밀번호가 일치하지 않습니다."
		
		
		return 0;
	}

	//회원 탈퇴 서비스 구현
	@Override
	public int secession(Member loginMember) {
		
		//1)  DB에서 현재 회원의 비밀번호를 조회
				//	-> 회원 비밀번호는 db에만 있기 대문에 db에서 꺼내와야함
				String encPw = dao.selectEncPw(loginMember.getMemberNo());
				
				if(bcrypt.matches(loginMember.getMemberPw(), encPw)) {
					//2) 비밀번호가 일치하면 회원번호를 이용해서 탈퇴 진행
				
					return dao.secession(loginMember.getMemberNo());
				}
				
				//3) 비밀번호가 일치하지 않으면 0 반환하고 끝
				return 0;
	}
	
	//프로필 이미지 수정 서비스 구현

	@Override
	public int updateProfile(Map<String, Object> map) throws IOException {
							//webPath, folderPath, uploadImage, delete(String), emeberNo
		//자주 쓰는 값 변수에 저장
		MultipartFile uploadImage = (MultipartFile)map.get("uploadImage");
		String delete = (String)map.get("delete"); //"0"(변경) / "1"(삭제)
		
		// 프로필 이미지 삭제 여부를 확인해서 
		// 삭제가 아닌 경우(== 새 이미지로 변경) -> 업로드된 파일명 변경
		// 삭제된 경우 -> null 값을 준비 
		
		
		String renameImage = null; //변경된 파일명 저장할 변수
		
		if(delete.equals("0")) { //이미지가 변경된 경우
			
			//파일명 변경할 것이다.
			//uploadImage.getOriginalFilename() : 원본 파일명
			renameImage = Util.fileRename(uploadImage.getOriginalFilename()); 
			
			map.put("profileImage", map.get("webPath")+renameImage);
			
			
			
		}else {
			
			map.put("profileImage", null);
			
		}
		
		//DAO를 호출해서 프로필 이미지 수정
		int result = dao.updateProfile(map);
		
		//--------- mapple 다녀옴
		//DB 수정 성공 시 메모리에 임시 저장되어 있는 파일을 서버에 저장한다
		
		if(result > 0 && map.get("profileImage") != null) {
			uploadImage.transferTo(new File(map.get("folderPath") + renameImage));
			
		}
		
		return result;
	}
	
	
	

}
