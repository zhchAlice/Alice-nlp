package edu.zhch.nlp.mongodb.model;

public class WordBaseInfoOfOnePage {
	private String pageId;
	private Integer wordNumOfThisPage;
	private Double wordTfIdfOfThisPage;
	
	public WordBaseInfoOfOnePage(String pageId) {
		super();
		this.pageId = pageId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;	
	}
	
	public Integer getWordNumOfThisPage() {
		return wordNumOfThisPage;
	}
	public void setWordNumOfThisPage(Integer wordNumOfThisPage) {
		this.wordNumOfThisPage = wordNumOfThisPage;
	}
	public Double getWordTfIdfOfThisPage() {
		return wordTfIdfOfThisPage;
	}
	public void setWordTfIdfOfThisPage(Double wordTfIdfOfThisPage) {
		this.wordTfIdfOfThisPage = wordTfIdfOfThisPage;
	}
}
