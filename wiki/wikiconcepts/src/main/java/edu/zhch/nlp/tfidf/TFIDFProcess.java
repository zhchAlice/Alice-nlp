package edu.zhch.nlp.tfidf;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.*;

import edu.zhch.nlp.mongodb.model.OneWeightedWordOfThisPage;
import edu.zhch.nlp.mongodb.model.PageBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfoOfOnePage;
import edu.zhch.nlp.mongodb.service.PageBaseInfoService;
import edu.zhch.nlp.mongodb.service.WordBaseInfoService;

public class TFIDFProcess {
	@SuppressWarnings("restriction")
	@Resource(name="wordBaseInfoService")
	private static WordBaseInfoService wordBaseInfoService;
	
	@SuppressWarnings("restriction")
	@Resource(name="pageBaseInfoService")
	private static PageBaseInfoService pageBaseInfoService;
	
	public static void main(String[] args) {		
		calculateWordTFIDF();
		
		generatePageConcepts();
	}
		
	public static void calculateWordTFIDF() {
		List<WordBaseInfo> allWords = wordBaseInfoService.findAllWordsBaseInfo();
		int allPageNum = pageBaseInfoService.getAllPagesNum();
		
		for(WordBaseInfo word : allWords) {
			List<WordBaseInfoOfOnePage> wordBaseInfoOfAllPages =
					word.getWordBaseInfoOfAllPages();
			String wordId = word.getWordId();
			int allPageNumContainThisWord = wordBaseInfoOfAllPages.size();
			Double idf = Math.log(Double.valueOf(allPageNum) / 
					Double.valueOf(allPageNumContainThisWord+1));
			
			for(WordBaseInfoOfOnePage wordBaseInfoOfOnePage : wordBaseInfoOfAllPages) {
				String pageId = wordBaseInfoOfOnePage.getPageId();
				int pageTotalNum = pageBaseInfoService.getPageTotalWordNum(pageId);
				Double tf = Double.valueOf(wordBaseInfoOfOnePage.getWordNumOfThisPage()) /
						Double.valueOf(pageTotalNum);
				wordBaseInfoOfOnePage.setWordTfIdfOfThisPage(tf*idf);
				pageBaseInfoService.addWeightedWordToPage(pageId, wordId, tf*idf);
			}
			wordBaseInfoService.updateWordIdf(wordId, idf);
			wordBaseInfoService.updateWholePagesOfThisWord(wordId, wordBaseInfoOfAllPages);
		}		
	}
	
	public static void generatePageConcepts() {
		List<PageBaseInfo> allPages = pageBaseInfoService.findAll();
		for(PageBaseInfo page : allPages) {
			List<OneWeightedWordOfThisPage> allWeightWordsOfThisPage = page.getAllWeightedWords();
			
			Collections.sort(allWeightWordsOfThisPage, new Comparator<OneWeightedWordOfThisPage>() {
				public int compare(OneWeightedWordOfThisPage arg0,
						OneWeightedWordOfThisPage arg1) {
					return arg1.getWordWeight().compareTo(arg0.getWordWeight());
				}			
			});
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i<2; i++) {
				sb.append(allWeightWordsOfThisPage.get(i));
				sb.append(" ");
			}
			sb.append(allWeightWordsOfThisPage.get(2));
			pageBaseInfoService.updatePageConcept(page.getPageId(), sb.toString());
		}
	}

}
