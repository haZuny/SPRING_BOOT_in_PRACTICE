package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class HelloApplication {

	// 컨트롤러 클래스 생성
	@Controller
	public class MyController{
		// 도메인 주소 매핑, 해당 주소 접근시 동작 정의
		@GetMapping("mvc-page")
		public String mvcPageMapping(@RequestParam("val") String val, Model model){
			model.addAttribute("mvc_val", val);
			return "MVC_view";
		}

		// API를 사용하여 웹 개발
		@GetMapping("api-page")
		@ResponseBody	// 리턴 값을, http의 바디로 바로 적용, 데이터만 바로 전송
		public String apiPageMapping(@RequestParam("val") String val){
			return "api를 통해 넘겨받은 값은 " + val;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

}