package com.example.hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // MyController 클래스를 컨트롤러로 지정
public class MyController {
    @GetMapping("sub-page") // "sub-page"라는 url 주소를 매핑
    public String subPage(Model model){
        // "12345678"이라는 값을 "data"라는 이름으로 지정 및 추가
        model.addAttribute("data", "12345678");
        // templates 폴터에서 "sub-page.html" 이름을 가지는 템플릿으로 연결
        return "sub-page";
    }

    @GetMapping("")
    public String wellcomePage(Model model){
        model.addAttribute("data2", "87654321");
        return "wellcome";
    }
}
