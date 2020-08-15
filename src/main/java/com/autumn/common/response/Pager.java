package com.autumn.common.response;

public class Pager {

	private int totalRecords = 0;

	private int currPage = 1;

	private int pageSize = 10;

	private int offset = 0;

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurPage() {
		return currPage;
	}

	public void setCurPage(int curPage) {
		this.currPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return pageSize * currPage - pageSize;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
