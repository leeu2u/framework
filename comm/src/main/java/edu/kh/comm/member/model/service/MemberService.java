package edu.kh.comm.member.model.service;

import java.util.List;

import edu.kh.comm.member.model.vo.Member;

/*
 * Service Interface를 사용하는 이유 
 * 
 * 1. 프로젝트에 규칙성을 부여하기 위해서 
 * 2. Spring AOP를 위해서 필요 
 * 3. 클래스간의 결합도를 약화 시키기 위해서 
 * 
 */

public interface MemberService {
	
	//모든 메서드가 추상 메서드 (묵시적으로 public abstract)
	//모든 필드는 상수 			(묵시적으로 public static final)
	
	
	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	public abstract Member login(Member inputMember);


	
	
	/** 이메일 중복검사 서비스
	 * @param memberEmail
	 * @return result
	 */
	public abstract int emailDupCheck(String memberEmail);
	//-> impl 에러났기 때문에 impl가서 오버라이드 해주기




	/** 닉네임 중복검사 서비스 
	 * @param memberNickname
	 * @return result
	 */
	public abstract int nicknameDupCheck(String memberNickname);




	/** 회원가입하기 
	 * @param memberSignUp
	 * @return
	 */
	public abstract int signUp(Member memberSignUp);




	/** 전체 회원 정보 가져오기
	 * @return
	 */
	public abstract List<Member> memList();




	/** 1명 회원 정보 가져오기
	 * @param memberEmail
	 * @return
	 */
	public abstract Member selectOne(String memberEmail);
	

}




