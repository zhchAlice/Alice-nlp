package edu.zhch.nlp.wiki.filter;

import java.util.ArrayList;
import java.util.List;

public class StopRareWordFilter implements IFilter {
	private List<String> stopWordList;
	
	private List<String> rareWordList;

	public boolean doFilter(FilterRequest request) {
		List<String> remainWords = new ArrayList<String>();
		List<String> inputWords = request.getInputWords();
		for(String word : inputWords) {
			if(!stopWordList.contains(word) && !rareWordList.contains(word)) {
				remainWords.add(word);
			}
		}
		request.setInputWords(remainWords);
		return true;
	}

	public List<String> getStopWordList() {
		return stopWordList;
	}

	public void setStopWordList(List<String> stopWordList) {
		this.stopWordList = stopWordList;
	}

	public List<String> getRareWordList() {
		return rareWordList;
	}

	public void setRareWordList(List<String> rareWordList) {
		this.rareWordList = rareWordList;
	}
}
