package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DemoService

@RestController
@RequestMapping("/")
public class DemoController {
	
	@Autowired
	DemoService demoService
	
	@PostMapping("/build")
	def build(@RequestBody def data) {
		demoService.build(data)
	}
}
