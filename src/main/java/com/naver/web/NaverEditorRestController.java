package com.naver.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naver.service.NaverEditorService;

@RestController
public class NaverEditorRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NaverEditorRestController.class);
	
	@Resource(name="NaverEditorService")
	private NaverEditorService naverEditorService;
	
	@PostMapping("/insertEditor.json")
	public Map<String, String> insertEditor(HttpServletRequest request){
		Map<String, String> res = new HashMap<String, String>();
		
		try {
			res.put("DATA", request.getParameter("content"));
			res.put("MSG", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("MSG", "FAIL");
		}
		
		return res;
	}
	
	@PostMapping("/updateEditor.json")
	public Map<String, String> updateEditor(HttpServletRequest request){
		Map<String, String> res = new HashMap<String, String>();
		
		try {
			res.put("DATA", request.getParameter("content"));
			res.put("MSG", "SUCCESS");
		} catch (Exception e) {
			LOGGER.debug(e.toString());
			res.put("MSG", "FAIL");
		}
		
		return res;
	}
}
