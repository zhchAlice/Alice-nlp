package edu.zhch.nlp.wiki.filter;

import java.util.ArrayList;
import java.util.List;

import edu.zhch.nlp.wiki.parse.PorterStemmer;

public class PorterStemmerFilter implements IFilter {
	
	private PorterStemmer porterStemmer;

	public boolean doFilter(FilterRequest request) {
		List<String> inputWords = request.getInputWords();
		List<String> outputWords = new ArrayList<String>();
		for(String word : inputWords) {
			String lowerWord = word.toLowerCase();
			porterStemmer.add(lowerWord.toCharArray(), lowerWord.length());
			porterStemmer.stem();
			outputWords.add(porterStemmer.toString());
		}
		request.setInputWords(outputWords);
		return false;
	}

	public PorterStemmer getPorterStemmer() {
		return porterStemmer;
	}

	public void setPorterStemmer(PorterStemmer porterStemmer) {
		this.porterStemmer = porterStemmer;
	}
}
