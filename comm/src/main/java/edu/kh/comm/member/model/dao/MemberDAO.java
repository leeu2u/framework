package edu.kh.comm.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.comm.member.model.vo.Member;

@Repository // 영속성을 가지는 DB/파일과 연결되는 클래스임을 명시 + bean 등록 
public class MemberDAO {
	
	
	/*
	 * DAO는 DB랑 연결하기 위한 Connection이 공통적으로 필요하다. 
	 * -> 필드에 선언할 예정
	 * + Mybatis 영속성 프레임워크를 통해서 이용하려면 Connection을 이용해 만든 객체
	 * SqlSessionTemplate을 사용
	 * 
	 */
	
	



		
	//db 연결해야하기때문에 connection 필요
	@Autowired //root-context.xml에서 생성된 SqulSessionTemplate bean을 의존성 주입(DI)
	private SqlSessionTemplate sqlSession; //(==connection)
	
	private Logger logger = LoggerFactory.getLogger(MemberDAO.class);
	

	public List<Member> selectAll;

	
	

	public Member login(Member inputMember) {
		
		//1행 조회(파라미터 x) 방법
//		int count = sqlSession.selectOne("namespace값.id값");
//		int count = sqlSession.selectOne("memberMapper.test1");
//		logger.debug(count + "");
		
//		//1행 조회(파라미터 o) 방법
//		// 이메일값을 전달하면 해당 하는 사람 이름을 갖고오기
//		String memberNickname = sqlSession.selectOne("memberMapper.test2", inputMember.getMemberEmail());
//		logger.debug(memberNickname);
//		
//		//1행 조회(파라미터가 VO인 경우)
//		String memberTel = sqlSession.selectOne("memberMapper.test3", inputMember);
//													//inputmember 안에는 memberEmail, memberPw 존재
//		
//		logger.debug(memberTel);
//		
//		return null;
		
		
		//------------------- 실제로 로그인하는 방법
		// 1행 조회(파라미터 VO, 반환되는 결과 VO)
//		
		Member loginMember = sqlSession.selectOne("memberMapper.login", inputMember);
		
		return loginMember;
		
		
	}

	
	/** 이메일 중복검사 dao
	 * @param memberEmail
	 * @return result
	 */
	public int emailDupCheck(String memberEmail) {
		return sqlSession.selectOne("memberMapper.emailDupCheck", memberEmail);
	}


	/** 닉네임 중복검사 dao
	 * @param memberNickname
	 * @return
	 */
	public int nicknameDupCheck(String memberNickname) { // 닉네임 받은것 밑으로 파라미터 넘겨주기
		return sqlSession.selectOne("memberMapper.nicknameDupCheck", memberNickname);
	}

	
	/**회원가입DAO**/

	public int signUp(Member memberSignUp) {
		return sqlSession.insert("memberMapper.signUp", memberSignUp);
	}
	
	//Insert, update, delete 반환값 int 형으로 고정
	//-> mapper에서도 resultType 항상 _int 고정
	//-> resultType 생략 가능(묵시적으로 _int)


	public Member selectOne(String memberEmail) {
	    return sqlSession.selectOne("memberMapper.selectOne", memberEmail);
	}


	public List<Member> selectAll() {
		return sqlSession.selectList("memberMapper.selectAll");
	}


	





	


}
