package project.moseup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@RequestMapping("/")
	public String main() {
		System.out.println("main 지나간당~");
		return "main";
	}

}