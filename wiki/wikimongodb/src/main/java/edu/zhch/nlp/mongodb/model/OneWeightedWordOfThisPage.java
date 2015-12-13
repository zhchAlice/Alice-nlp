package edu.zhch.nlp.mongodb.model;

public class OneWeightedWordOfThisPage {
	private String wordId;
	
	private Double wordWeight;
	
	public String getWordId() {
		return wordId;
	}
	
	public OneWeightedWordOfThisPage(String wordId, Double wordWeight) {
		super();
		this.wordId = wordId;
		this.wordWeight = wordWeight;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public Double getWordWeight() {
		return wordWeight;
	}
	public void setWordWeight(Double wordWeight) {
		this.wordWeight = wordWeight;
	}
	
}
