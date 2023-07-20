package com.naver.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.naver.service.NaverEditorService;

@Controller
public class NaverEditorController {

	@Resource(name="NaverEditorService")
	private NaverEditorService naverEditorService;
	
	@GetMapping("/")
	public String getMainPage() {
		return "main";
	}
	
	@GetMapping("/upload.do")
	public String getUploadPage() {
		return "upload";
	}
	
	@GetMapping("/update.do")
	public String getUpdatePage() {
		return "update";
	}
	
	@PostMapping("/uploadEditorImg.do")
	public void uploadEditorImg(HttpServletRequest request, HttpServletResponse response) {
		try {
			String res = naverEditorService.uploadEditorImg(request);
			response.getOutputStream().println(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/uploadEditorImgView.do/{fileName}")
	public void uploadEditorImgView(HttpServletRequest request, HttpServletResponse response
			, @PathVariable(name="fileName", required=true) String fileName) {
		try {
			File imgFile = naverEditorService.uploadEditorImgView(fileName);
			String imgFileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			
			ServletOutputStream out = response.getOutputStream();
			response.setContentType(imgFileExt);
			
			out.write(Files.readAllBytes(imgFile.toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
