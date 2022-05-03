package kr.bizdata.core.servlet.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import kr.bizdata.core.util.FileUtil;

public class ExcelXlsxView extends AbstractXlsxView {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> excelData = (Map) request.getAttribute("excelData");
		
		String fileName = excelData.get("fileName") + ".xlsx";
		String[] headers = (String[]) excelData.get("headers");
		String[] headers2 = (String[]) excelData.get("headers2");
		String[] headers3 = (String[]) excelData.get("headers3");
		String[] footers = (String[]) excelData.get("footers");
		String[] dataKeys = (String[]) excelData.get("dataKeys");
		List<Map<String, Object>> dataList = (List) excelData.get("dataList");
		
		Assert.notEmpty(headers, "headers is empty.");
		Assert.notEmpty(dataKeys, "dataKeys is empty.");
		Assert.isTrue(headers.length == dataKeys.length, "length not match.");
		Assert.notNull(dataList, "dataList is null");
		
		int r = 0; // rownum 0부터 시작
		
		// sheet 생성
		Sheet sheet = workbook.createSheet(fileName);
		
		// header 생성
		if (headers3 != null && headers3.length > 0) {
			createHeader3(r, sheet, headers, headers2, headers3);
			r += 3;
		}else if (headers2 != null && headers2.length > 0) {
			createHeader2(r, sheet, headers, headers2);
			r += 2;
		} else {
			createHeader1(r, sheet, headers);
			r += 1;
		}
		// data 목록 생성
		createData(r, sheet, dataKeys, dataList);
		
		// footer 생성
		if(footers != null) {
			r += dataList.size();
			createFooter(r,sheet,footers);
		}
		
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn((short) i, true); //autoSizeColumn(int column, boolean useMergedCells)
		}
		
		FileUtil.setDownloadHeader(request, response, fileName);
	}
	
	// header 생성
	private void createHeader1(int r, Sheet sheet, String[] headers) {
		
		CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
		Row row = sheet.createRow(r);
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}
	}
	
	// header 생성
	private void createHeader2(int r, Sheet sheet, String[] headers, String[] headers2) {
		
		CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
		Row row = sheet.createRow(r);
		
		int r1 = r;
		int r2 = r + 1;
		int start = 0;
		int end = 0;
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headers[i]);
			if (headers[start].toString() == headers[i].toString()) {
				end = i;
			} else {
				if (start != end) {
					sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end));
				}
				start = i;
				end = i;
			}
		}
		if (start != end) {
			sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end));
		}
		row = sheet.createRow(r2);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			if (headers[i].toString() == headers2[i].toString()) {
				sheet.addMergedRegion(new CellRangeAddress(r1, r2, i, i));
			} else {
				cell.setCellValue(headers2[i]);
			}
		}
	}
	
	// header3 생성 (새로추가한 부분- 엑셀의 헤더가 3개이고 셀병합이 이루어질때 사용. 기존 header 생성함수 참고)
	private void createHeader3(int r, Sheet sheet, String[] headers, String[] headers2, String[] headers3) {
		CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
		Row row = sheet.createRow(r);
		
		int r1 = r;
		int r2 = r + 1;
		int r3 = r2 + 1;
		int start = 0;
		int end = 0;		
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headers3[i]);
			if (headers3[start].toString() == headers3[i].toString()) {
				end = i;
			} else {
				if (start != end) {
					sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end)); //(첫행, 마지막행, 첫열, 마지막열)
				}
				start = i;
				end = i;
			}
		}
		if (start != end) {
			sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end));
		}
		
		r1 = r;
		r2 = r+1;
		r3 = r2+1;
		start = 0;
		end = 0;
		
		row = sheet.createRow(r2);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);			
			cell.setCellValue(headers2[i]);
			if (headers2[start].toString() == headers2[i].toString()) {
				end = i;
			} else {
				if (start != end) {
					sheet.addMergedRegion(new CellRangeAddress(r2, r2, start, end));
				}
				start = i;
				end = i;
			}
			
		}
		if (start != end) {
			sheet.addMergedRegion(new CellRangeAddress(r2, r2, start, end));
		}
		row = sheet.createRow(r3);
				
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			
			if (headers[i].toString() == headers2[i].toString() && headers2[i].toString() == headers3[i].toString() ) { 
				 sheet.addMergedRegion(new CellRangeAddress(r1, r3, i, i));
			 }
			 else if (headers[i].toString() == headers2[i].toString() && headers2[i].toString() != headers3[i].toString()) {
				sheet.addMergedRegion(new CellRangeAddress(r2, r3, i, i));
			 }
			 else if(headers2[i].toString() == headers3[i].toString() && headers[i].toString() != headers2[i].toString()) {
				sheet.addMergedRegion(new CellRangeAddress(r1, r2, i, i));
			 } else { 
				 cell.setCellValue(headers[i]); 
			 }
		}	
		
	}	
	
	// data 목록 생성
	private void createData(int r, Sheet sheet, String[] dataKeys, List<Map<String, Object>> dataList) {
		
		CellStyle dataStyle = createDataStyle(sheet.getWorkbook());
		
		for (Map<String, Object> data : dataList) {
			Row row = sheet.createRow(r);
			for (int i = 0; i < dataKeys.length; i++) {
				Object value = data.get(dataKeys[i]);
				
				Cell cell = row.createCell(i);
				if (value == null) {
					// not set
				} else if (value instanceof Date) {
					cell.setCellValue((Date) value);
				} else if (value instanceof Number) {
					cell.setCellValue(NumberUtils.toDouble(String.valueOf(value)));
				} else {
					cell.setCellValue(value.toString());
				}
				cell.setCellStyle(dataStyle);
			}
			r++; // 증가
		}
	}
	
	/*
		footer 생성 
		기존 header 만드는 방식과 로직은 같지만 footer의 모양에 따라 코드 바꿔줘야함.
		현재 아래 footer생성 함수는 행이 1개이고 앞에 2열만 merge하는 코드임. 
	*/
	private void createFooter(int r, Sheet sheet, String[] footers) {
		
		CellStyle headerStyle = createHeaderStyle(sheet.getWorkbook());
		Row row = sheet.createRow(r);
		
		int r1 = r;
		int start = 0;
		int end = 0;
		
		for (int i = 0; i < footers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(footers[i]);
			
			if (footers[start].toString() == footers[i].toString()) {
				end = i;
			} else {
				if (start != end && i<3) { //첫번째, 두번째 컬럼만 merge하기 위해 i<3 조건 추가.
					sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end));
				}
				start = i;
				end = i;
			}		
		}
		if (start != end) {
			sheet.addMergedRegion(new CellRangeAddress(r1, r1, start, end));
		}
		
	}
	
	// Header Cell Style
	private CellStyle createHeaderStyle(Workbook workbook) {
		
		CellStyle style = workbook.createCellStyle();
		
		// Font
		Font font = workbook.createFont();
		font.setFontName("돋움");
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		style.setFont(font);
		
		// Align
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// Border
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		
		// Border Color
		style.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
		
		style.setFillForegroundColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		return style;
	}
	
	// Data Cell Style
	private CellStyle createDataStyle(Workbook workbook) {
		
		CellStyle style = workbook.createCellStyle();
		
		// Font
		Font font = workbook.createFont();
		font.setFontName("돋움");
		font.setFontHeightInPoints((short) 10);
		style.setFont(font);
		
		// Align
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// Border
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		
		// Border Color
		style.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
		style.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
		
		// 데이터 형식 맞춤
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("TEXT")); // 텍스트 형식
		//style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0")); // 금액 데이터 -> 소수점이 반올림되어 표시되는 문제가 있음.
		
		return style;
	}
	
}
