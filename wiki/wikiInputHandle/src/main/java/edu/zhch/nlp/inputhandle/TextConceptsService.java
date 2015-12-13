package edu.zhch.nlp.inputhandle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.*;

import org.springframework.stereotype.Service;

import edu.zhch.nlp.mongodb.model.PageBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfoOfOnePage;
import edu.zhch.nlp.mongodb.service.PageBaseInfoService;
import edu.zhch.nlp.mongodb.service.WordBaseInfoService;

@Service("textConceptsService")
public class TextConceptsService {
	
	@Resource(name="wordBaseInfoService")
	private  WordBaseInfoService wordBaseInfoService;
	
	@Resource(name="pageBaseInfoService")
	private PageBaseInfoService pageBaseInfoService;
	
	public Map<String, Double> generateWeightedConcepts(int wordsNumofInputText,
			Map<String, Integer> inputText) {
		
		Map<String, Double> inputTextWeightedConcepts = initWeightedConceptsMap();
		
		for(Map.Entry<String, Integer> word : inputText.entrySet()) {
			WordBaseInfo wordBaseInfo = wordBaseInfoService.findByName(word.getKey());
			String wordId = wordBaseInfo.getWordId();
			Double wordTfIdf = calculateWordTfIdf(wordId,word.getValue(),wordsNumofInputText);
			List<WordBaseInfoOfOnePage> wordRelatedPages= wordBaseInfo.getWordBaseInfoOfAllPages();
			for(WordBaseInfoOfOnePage wordRelatedPage : wordRelatedPages) {
				Double pageConceptWeight = wordRelatedPage.getWordTfIdfOfThisPage();
				String pageConcept = pageBaseInfoService.getPageConcept(wordRelatedPage.getPageId());
				Double oldConceptWeight = inputTextWeightedConcepts.get(pageConcept);
				inputTextWeightedConcepts.put(pageConcept, (oldConceptWeight + wordTfIdf*pageConceptWeight));
			}
		}
		return inputTextWeightedConcepts;
	}
	
	private Double calculateWordTfIdf(String wordId, int wordNumOfThisPage, int pageTotalWordNum) {
		Double tf = Double.valueOf(wordNumOfThisPage) / Double.valueOf(pageTotalWordNum);
		Double idf = wordBaseInfoService.getWordIdfValue(wordId);
		return tf*idf;
		
	}
	
	private Map<String, Double> initWeightedConceptsMap() {
		Map<String, Double> weightedConceptsMap = new HashMap<String, Double>();
		List<PageBaseInfo> allPages = pageBaseInfoService.findAll();
		for(PageBaseInfo page : allPages) {
			weightedConceptsMap.put(page.getPageConcept(), Double.valueOf(0));
		}
		return weightedConceptsMap;
	}
	
	public double computeConsineSimilarity(Map<String, Double> weightedConceptsOne,
			Map<String, Double> weightedConceptsTwo) {
		double dotProduct = 0.0;   
        double magnitudeOne = 0.0;   
        double magnitudeTwo = 0.0;   
        double cosineSimilarity = 0.0;   
        
		Iterator<Entry<String, Double>> iteratorOne = weightedConceptsOne.entrySet().iterator();
		Iterator<Entry<String, Double>> iteratorTwo = weightedConceptsTwo.entrySet().iterator();
		while(iteratorOne.hasNext()) {
			Map.Entry<String, Double> entryOne = iteratorOne.next();
			Map.Entry<String, Double> entryTwo = iteratorTwo.next();
			dotProduct += entryOne.getValue() * entryTwo.getValue();
			magnitudeOne = Math.pow(entryOne.getValue(), 2);
			magnitudeTwo = Math.pow(entryTwo.getValue(), 2);
		}
		magnitudeOne = Math.sqrt(magnitudeOne);
		magnitudeTwo = Math.sqrt(magnitudeTwo);
		if(magnitudeOne != 0 || magnitudeTwo !=0) {
			cosineSimilarity = dotProduct/(magnitudeOne * magnitudeTwo);
		} else {
			cosineSimilarity = 0;
		}
		return cosineSimilarity;
	}
	
}
