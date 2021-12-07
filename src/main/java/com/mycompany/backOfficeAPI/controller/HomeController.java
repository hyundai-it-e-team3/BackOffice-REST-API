package com.mycompany.backOfficeAPI.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Resource(name = "memberDBDataSource")
	private DataSource memberDataSource;
	
	@Resource(name = "productDBDataSource")
	private DataSource productDataSource;
	
	@Resource(name = "orderDBDataSource")
	private DataSource orderDataSource;
	
	
	@RequestMapping("/")
	public String home(){
		return "home";
		
	}		
	
	@GetMapping("/testConnectToDB")
	public String testConnectToDB() throws SQLException {
		Connection connMember = memberDataSource.getConnection();
		log.info("memberDB 연결 성공");
		connMember.close();
		
		Connection connProduct = productDataSource.getConnection();
		log.info("productDB 연결 성공");
		connProduct.close();
		
		Connection connOrder = orderDataSource.getConnection();
		log.info("orderDB 연결 성공");
		connOrder.close();
		
		return "redirect:/";
	}
}

