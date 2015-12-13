package edu.zhch.nlp.wiki.filter;

import java.util.HashSet;
import java.util.List;

public class FilterRequest {
	
	private String pageId;
	
	private List<String> inputWords;
	
	private HashSet<String> outLinks;

	public FilterRequest(String pageId, List<String> inputWords,
			HashSet<String> outLinks) {
		super();
		this.pageId = pageId;
		this.inputWords = inputWords;
		this.outLinks = outLinks;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public List<String> getInputWords() {
		return inputWords;
	}

	public void setInputWords(List<String> inputWords) {
		this.inputWords = inputWords;
	}

	public HashSet<String> getOutLinks() {
		return outLinks;
	}

	public void setOutLinks(HashSet<String> outLinks) {
		this.outLinks = outLinks;
	}

}
