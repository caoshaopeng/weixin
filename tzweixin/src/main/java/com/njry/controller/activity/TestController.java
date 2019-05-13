package com.njry.controller.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njry.controller.BaseController;

@RequestMapping(value = "Test/")
@Controller
public class TestController extends BaseController {
	
	@RequestMapping(value = "frame.do")
	public String frame() {
		
		return PATH + "test/test";
	}
	
}
