package edu.zhch.nlp.wiki.filter;

import java.util.List;

import edu.zhch.nlp.mongodb.model.PageBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfo;
import edu.zhch.nlp.mongodb.service.PageBaseInfoService;
import edu.zhch.nlp.mongodb.service.WordBaseInfoService;;

public class WordCollectionFilter implements IFilter {
	
	private WordBaseInfoService WordBaseInfoService;
	
	private PageBaseInfoService pageBaseInfoService;

	public boolean doFilter(FilterRequest request) {
		String pageId = request.getPageId();
		List<String> inputWords = request.getInputWords();
		for(String word : inputWords) {
			WordBaseInfo wordBaseInfo = WordBaseInfoService.findByName(word);
			if(null == wordBaseInfo) {
				WordBaseInfoService.generateAndinsertFirstEntity(word, pageId);
			} else {
				WordBaseInfoService.incWordNumOfOnePage(wordBaseInfo.getWordId(), pageId);
			}			
		}
		
		PageBaseInfo pageBaseInfo = new PageBaseInfo(pageId, inputWords.size());
		pageBaseInfoService.insertOnePage(pageBaseInfo);
		return false;
	}

	public void setWordBaseInfoService(WordBaseInfoService wordBaseInfoService) {
		WordBaseInfoService = wordBaseInfoService;
	}

	public void setPageBaseInfoService(PageBaseInfoService pageBaseInfoService) {
		this.pageBaseInfoService = pageBaseInfoService;
	}
}
