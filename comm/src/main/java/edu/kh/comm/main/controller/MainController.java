package edu.kh.comm.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	/*
	 * - 목표경로
	 * http://localhost:8080/comm/main
	 */
	
	@RequestMapping("/main")
	public String mainForward() {
		
		return "common/main";
		//요기가 controller니까 여기서-> view로 넘어간다~~
	}

}
