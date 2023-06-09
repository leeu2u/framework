package edu.kh.comm.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter(filterName="initFilter", urlPatterns = "/*")
public class InitFilter extends HttpFilter implements Filter {
	
	//Logger 객체 / Debug Mode  사용
	
	//Logger 객체 생성 (해당 클래스에 대한 log를 출력하는 객체)
	private Logger logger = LoggerFactory.getLogger(InitFilter.class); //해당 폴더 이름
	
	//init은 필터가 생성될 때 실행
	public void init(FilterConfig fConfig) throws ServletException {
		//logger를 이용해서 출력하는 방법
		// - logger안에 아래의 메소드가 존재함
		//trace - debug - info - warn - error
		// 보통 debug, info, error 주로 사용
		
		
		//debug : 개발의 흐름 파악할 때 (해당 코드가 실행이 되었는지, 파라미터가 어떤 값을 가지고있는지 확인할때)
		//info : 메소드가 실행되었는지 파악할 때 사용
		
		
		logger.info("초기화 필터 생성");
		// 실행된다면 "초기화 필터 생성" 이 뜬다면! -> init 메소드가 실행된것임!
	}
	
	//---------------------------------------------------------------

	// destroy는 필터가 파괴될 때 실행(back-end 코드가 수정될 때 ==  파괴)
	// == 서버는 켜져있는데 백엔드 코드가 수정되었을 때 
	public void destroy() {
		logger.info("초기화 필터 파괴");
	}
	
	
	//------------------------------------------------------------
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {	
		// application 내장 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		// 최상위 주소 얻어오기
		String contextPath =  ( (HttpServletRequest)request ).getContextPath();
									// 다운캐스팅
		// 세팅
		application.setAttribute("contextPath", contextPath);
		
		chain.doFilter(request, response);
	}

	

}
