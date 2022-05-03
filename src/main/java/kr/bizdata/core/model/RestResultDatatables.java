package kr.bizdata.core.model;

import java.util.List;

/**
 * Datatables Server-side processing
 */
public class RestResultDatatables extends RestResult {
	
	private int recordsTotal;    // Datatables Server-side Returned data
	private int recordsFiltered; // Datatables Server-side Returned data
	private int draw;            // Datatables Server-side Returned data
	private int start;           // Datatables Server-side Request data (결과 목록의 시작 인덱스)
	
	public RestResultDatatables(List<?> data, int totalCnt, int draw, int start) {
		super(data);
		this.recordsTotal = totalCnt;
		this.recordsFiltered = totalCnt;
		this.draw = draw;
		this.start = start;
	}
	
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public int getDraw() {
		return draw;
	}
	public int getStart() {
		return start;
	}
	
}
