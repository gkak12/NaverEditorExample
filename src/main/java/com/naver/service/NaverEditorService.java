package com.naver.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public interface NaverEditorService {

	public String uploadEditorImg(HttpServletRequest request) throws Exception, IOException;
	
	public File uploadEditorImgView(String fileName) throws Exception;
}
