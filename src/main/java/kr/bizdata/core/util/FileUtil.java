package kr.bizdata.core.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import kr.bizdata.core.config.Config;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 파일의 확장자를 구한다.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null || !fileName.contains(".")) {
			return "";
		}
		
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * 파일의 전체경로를 구한다.
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileFullPath(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		Assert.doesNotContain(filePath, "..", "filePath contains a relative path.");
		
		String rootPath = Config.getString("bzd.file.rootPath");
		
		if (!rootPath.endsWith("/") && !filePath.startsWith("/")) {
			filePath = "/" + filePath;
		}
		
		return rootPath + filePath; // 전체경로
	}
	
	/**
	 * 파일을 읽는다.
	 * 
	 * @param filePath
	 * @return
	 */
	public static File readFile(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		
		File file = new File(FileUtil.getFileFullPath(filePath));
		
		if (file == null || !file.exists() || !file.isFile() || !file.canRead()) { // can not read file
			logger.error("Not found file. " + file);
			throw new RuntimeException("파일을 찾을 수 없습니다.");
		}
		
		return file;
	}
	
	/**
	 * 파일을 삭제한다.
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return;
		}
		
		File file = new File(FileUtil.getFileFullPath(filePath));
		
		if (file == null || !file.exists() || !file.isFile() || !file.canWrite()) { // can not write file
			return;
		}
		
		FileUtils.deleteQuietly(file); // 파일 삭제
	}
	
	/**
	 * 파일을 저장하고, 파일 경로를 반환한다.
	 * 
	 * @param middlePath 중간 저장 경로. ex) /2020/project
	 * @param file       업로드 파일
	 * @return
	 */
	public static String saveFile(String middlePath, MultipartFile file) {
		Assert.hasText(middlePath, "middlePath is blank.");
		Assert.doesNotContain(middlePath, "..", "middlePath contains a relative path.");
		Assert.notNull(file, "file is null.");
		Assert.isTrue(file.getSize() > 0, "file is empty.");
		
		String rootPath = Config.getString("bzd.file.rootPath");
		
		if (!rootPath.endsWith("/") && !middlePath.startsWith("/")) {
			middlePath = "/" + middlePath;
		}
		if (!middlePath.endsWith("/")) {
			middlePath = middlePath + "/";
		}
		
		String filePath = null;
		
		try {
			File dir = new File(rootPath + middlePath);
			dir.mkdirs(); // 저장할 경로의 디렉토리가 없을 경우, 경로를 생성한다.
			
			File temp = File.createTempFile(System.currentTimeMillis() + "_", "." + getFileExt(file.getOriginalFilename()), dir);
			
			filePath = middlePath + temp.getName();
			
			file.transferTo(temp); // 파일 저장
			
			return filePath;
		} catch (Exception e) {
			String msg = "saveFile() failed. (" + file.getOriginalFilename() + ", " + filePath + ")";
			throw new RuntimeException(msg, e);
		}
	}
	
	/**
	 * 파일 다운로드 시, 응답 헤더 설정
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 */
	public static void setDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			fileName = "noname";
		}
		
		// 1. 파일 MIME 타입
		String mimeType = request.getSession().getServletContext().getMimeType(fileName);
		if (StringUtils.isEmpty(mimeType)) {
			mimeType = "application/octet-stream";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setDownloadHeader() mimeType = " + mimeType);
		}
		
		// 2. 파일명 인코딩 - 한글 깨짐 방지
		fileName = encodeFileName(request, response, fileName);
		
		// 3. 응답 헤더 성정
		response.reset();
		response.setHeader("Content-Type", mimeType + "; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//response.setHeader("Content-Length", ""+file.length());
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
	}
	
	/**
	 * PDF 출력 시, 응답 헤더 설정
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 */
	public static void setPdfHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			fileName = "noname.pdf";
		}
		
		// 파일명 인코딩 - 한글 깨짐 방지
		fileName = encodeFileName(request, response, fileName);
		
		// 응답 헤더 성정
		response.reset();
		response.setHeader("Content-Type", "application/pdf; charset=UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//response.setHeader("Content-Length", ""+file.length());
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
	}
	
	// 파일명 인코딩 - 한글 깨짐 방지
	private static String encodeFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("encodeFileName(" + fileName + ")");
		}
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = (userAgent.contains("MSIE") || userAgent.contains("Trident"));
		boolean xhr = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		try {
			if (ie || xhr) {
				fileName = URLEncoder.encode(fileName, response.getCharacterEncoding()); // URL 인코드
				fileName = StringUtils.replace(fileName, "+", "%20"); // 공백(" ")이 "+"로 나타나는 문제 해결
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "8859_1"); // 한글을 아스키 코드값 변환
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(); // ignore
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("-> " + fileName);
		}
		return fileName;
	}
	
}
