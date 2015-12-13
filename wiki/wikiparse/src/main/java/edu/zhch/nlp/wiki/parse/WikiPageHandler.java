package edu.zhch.nlp.wiki.parse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import edu.zhch.nlp.wiki.filter.FilterChain;
import edu.zhch.nlp.wiki.filter.FilterConstants;
import edu.zhch.nlp.wiki.filter.FilterRequest;

@Service("wikiPageHandler")
public class WikiPageHandler implements PageCallbackHandler {
	
	private FilterChain filterchain;
	
	public void process(WikiPage page) {
		String pageId = page.getID();
		String wikiText = page.getText();
		HashSet<String> outLinks = page.getLinks();
		
		if(StringUtils.isEmpty(wikiText)) {
			return;
		}
		String[] wikiTextArray = wikiText.split(FilterConstants.WORD_SPLIT);
		List<String> wikiTextList = Arrays.asList(wikiTextArray);
		
		FilterRequest filterRequest= new FilterRequest(pageId, wikiTextList, outLinks);
		filterchain.doFilter(filterRequest);
	}

	public void setFilterchain(FilterChain filterchain) {
		this.filterchain = filterchain;
	}	
}
