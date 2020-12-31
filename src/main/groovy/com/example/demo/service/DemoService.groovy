package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate

@Service
public class DemoService {

	@Autowired
	RestTemplate restTemplate

	def build(data) {
		restTemplate.getInterceptors().add(
				new BasicAuthorizationInterceptor("admin", "admin"));
		def crumbResponse = restTemplate.getForEntity("http://localhost:9876/crumbIssuer/api/json", Map.class)
		def crumbValue = crumbResponse.body.get("crumb")
		def crumbField = crumbResponse.body.get("crumbRequestField")
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON)
		headers.set("user", "admin:admin");
		headers.set("username", "admin");
		headers.set("password", "admin");
		println "Basic " + Base64.getEncoder().encodeToString("admin:admin".bytes)
		headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".bytes))
		headers.set(crumbField, crumbValue)// "d9fcea332b4a76e592d0f6937bd56dcf8c94ce6fc7db846810fff72632912264")
		HttpEntity entity = new HttpEntity("", headers);
		def response = restTemplate.postForEntity("http://localhost:9876/job/Test/build?token=11fb5af6393903b6a56f2bffabeb64373f&${crumbField}=${crumbValue}", entity, Map.class)
		println response.statusCode.is2xxSuccessful()
	}
}
