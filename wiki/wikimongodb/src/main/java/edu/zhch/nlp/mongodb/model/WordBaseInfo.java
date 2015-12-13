package edu.zhch.nlp.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WordBaseInfo {
	@Id
	private String wordId;
	
	private String wordName;
	
	private Double wordIdf;
	
	private List<WordBaseInfoOfOnePage> wordBaseInfoOfAllPages;
	
	public WordBaseInfo(String wordName) {
		super();
		this.wordName = wordName;
		this.wordIdf = Double.valueOf(0);
		this.wordBaseInfoOfAllPages = new ArrayList<WordBaseInfoOfOnePage>();
	}
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public Double getWordIdf() {
		return wordIdf;
	}
	public void setWordIdf(Double wordIdf) {
		this.wordIdf = wordIdf;
	}
	public List<WordBaseInfoOfOnePage> getWordBaseInfoOfAllPages() {
		return wordBaseInfoOfAllPages;
	}
	public void setWordBaseInfoOfAllPages(
			List<WordBaseInfoOfOnePage> wordBaseInfoOfAllPages) {
		this.wordBaseInfoOfAllPages = wordBaseInfoOfAllPages;
	}
}
