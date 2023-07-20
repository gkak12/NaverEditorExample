package com.naver.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.naver.service.NaverEditorService;

@Service("NaverEditorService")
public class NaverEditorServiceImpl implements NaverEditorService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NaverEditorServiceImpl.class);
	
	@Value("${img.file.dir}")
	private String imgFileDir;
	
	@Override
	public String uploadEditorImg(HttpServletRequest request) throws Exception, IOException{
		String res = null;
		String imgFileName = request.getHeader("file-name");
		String imgFileNameExt = imgFileName.substring(imgFileName.lastIndexOf(".")+1).toLowerCase();
		
		String[] imgFileExtArr = {"jpg", "png", "bmp", "gif"};
		boolean imgFileFlag = false;
		
		for(String imgFileExt : imgFileExtArr) {
			if(imgFileExt.equals(imgFileNameExt)) {
				imgFileFlag = true;
				break;
			}
		}
		
		if(imgFileFlag == false) {
			throw new Exception("imgFileExt is false.");
		} else {
			String imgFilePath = imgFileDir.concat(File.separator).concat(imgFileName);
			
			try (InputStream is = request.getInputStream();
				OutputStream os = new FileOutputStream(imgFilePath);){
				int readCnt = 0;
				byte[] buffer = new byte[1024];
				
				while((readCnt = is.read(buffer, 0, buffer.length)) != -1) {
					os.write(buffer, 0, readCnt);
				}
				
				os.flush();
				
				StringBuilder sb = new StringBuilder();
				sb.append("&bNewLine=true")
				.append("&sFileName=").append(imgFileName)
				.append("&sFileURL=").append("/uploadEditorImgView.do/").append(imgFileName);
				
				res = sb.toString();
				LOGGER.debug(res);
			} catch (IOException e) {
				throw new IOException(e.toString());
			} catch (Exception e) {
				throw new Exception(e.toString());
			}
		}
		
		return res;
	}

	@Override
	public File uploadEditorImgView(String fileName) throws Exception {
		File imgFile = null;
		
		try {
			imgFile = new File(imgFileDir.concat(File.separator).concat(fileName));
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		
		return imgFile;
	}
}
