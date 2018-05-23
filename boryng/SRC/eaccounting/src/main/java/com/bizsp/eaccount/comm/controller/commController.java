package com.bizsp.eaccount.comm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class commController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String scrnMain(){
		return "main";
	}
	
	
}
