package kr.bizdata.core.model;

/**
 * 페이징
 */
public class Paging {
	
	private String sortName; // 소팅기준
	private String sortType; // 소팅유형 (ASC: 오름차순, DESC: 내림차순)
	private int startIndex;  // 시작 인덱스
	private int rowsCnt;     // 한 페이지에 표시할 개수
	
	public Paging(String sortName, String sortType, int startIndex, int rowsCnt) {
		this.sortName = sortName;
		this.sortType = "DESC".equalsIgnoreCase(sortType) ? "DESC" : "ASC";
		this.startIndex = startIndex;
		this.rowsCnt = rowsCnt;
	}
	
	public String getSortName() {
		return sortName;
	}
	public String getSortType() {
		return sortType;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public int getRowsCnt() {
		return rowsCnt;
	}
	
}
