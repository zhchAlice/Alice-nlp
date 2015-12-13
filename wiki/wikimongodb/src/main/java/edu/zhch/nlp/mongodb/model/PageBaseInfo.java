package edu.zhch.nlp.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PageBaseInfo {
	private String pageId;
	
	private Integer pageTotalWordNum;
	
	private String pageConcept;
	
	private List<OneWeightedWordOfThisPage>  allWeightedWords;

	public PageBaseInfo(String pageId, Integer pageTotalWordNum) {
		super();
		this.pageId = pageId;
		this.pageTotalWordNum = pageTotalWordNum;
		this.allWeightedWords = new ArrayList<OneWeightedWordOfThisPage>();
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Integer getPageTotalWordNum() {
		return pageTotalWordNum;
	}

	public void setPageTotalWordNum(Integer pageTotalWordNum) {
		this.pageTotalWordNum = pageTotalWordNum;
	}

	public String getPageConcept() {
		return pageConcept;
	}

	public void setPageConcept(String pageConcept) {
		this.pageConcept = pageConcept;
	}

	public List<OneWeightedWordOfThisPage> getAllWeightedWords() {
		return allWeightedWords;
	}

	public void setAllWeightedWords(List<OneWeightedWordOfThisPage> allWeightedWords) {
		this.allWeightedWords = allWeightedWords;
	}
}
