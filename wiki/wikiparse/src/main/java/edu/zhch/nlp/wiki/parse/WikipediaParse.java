package edu.zhch.nlp.wiki.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.*;

import edu.jhu.nlp.wikipedia.WikiXMLParser;
import edu.jhu.nlp.wikipedia.WikiXMLParserFactory;
import edu.zhch.nlp.mongodb.service.PageBaseInfoService;
import edu.zhch.nlp.mongodb.service.WordBaseInfoService;
import edu.zhch.nlp.wiki.filter.FilterChain;
import edu.zhch.nlp.wiki.filter.FilterConstants;
import edu.zhch.nlp.wiki.filter.PageBaseFilter;
import edu.zhch.nlp.wiki.filter.PorterStemmerFilter;
import edu.zhch.nlp.wiki.filter.StopRareWordFilter;
import edu.zhch.nlp.wiki.filter.WordCollectionFilter;

public class WikipediaParse {
	
	@SuppressWarnings("restriction")
	@Resource(name="wikiPageHandler")
	private static WikiPageHandler handler;
	
	@SuppressWarnings("restriction")
	@Resource(name="wordBaseInfoService")
	private static WordBaseInfoService wordBaseInfoService;
	
	@SuppressWarnings("restriction")
	@Resource(name="pageBaseInfoService")
	private static PageBaseInfoService pageBaseInfoService;

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Usage: Parser <XML-FILE>");
			System.exit(-1);
		}		
		WikiXMLParser wxsp = null;
        try {
            wxsp = WikiXMLParserFactory.getSAXParser(args[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }
        
        FilterChain filterChain = new FilterChain();
        initFilterChain(filterChain);
        handler.setFilterchain(filterChain);

        try {
			wxsp.setPageCallback(handler);
			wxsp.parse();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	private static void initFilterChain(FilterChain filterChain) {
		PageBaseFilter pageBaseFilter = new PageBaseFilter();
		filterChain.addFilter(pageBaseFilter);
		
		StopRareWordFilter stopRareWordFilter = new StopRareWordFilter();
		initStopRareWordFilter(stopRareWordFilter);
		filterChain.addFilter(stopRareWordFilter);
		
		PorterStemmer porterStemmer = new PorterStemmer();
		PorterStemmerFilter porterStemmerFilter = new PorterStemmerFilter();
		porterStemmerFilter.setPorterStemmer(porterStemmer);
		filterChain.addFilter(porterStemmerFilter);
		
		WordCollectionFilter wordCollectionFilter = new WordCollectionFilter();
		wordCollectionFilter.setPageBaseInfoService(pageBaseInfoService);
		wordCollectionFilter.setWordBaseInfoService(wordBaseInfoService);
		filterChain.addFilter(wordCollectionFilter);
	}
	private static void initStopRareWordFilter(StopRareWordFilter filter) {
		
		List<String> stopWords = readFileWords(FilterConstants.STOPWORD_FILE_NAME);
		List<String> rareWords = readFileWords(FilterConstants.RAREWORD_FILE_NAME);
		
		filter.setStopWordList(stopWords);
		filter.setRareWordList(rareWords);		
	}
	
	private static List<String> readFileWords(String fileName) {
		
		List<String> wordList = new ArrayList<String>();
		
		File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
            	wordList.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }		
		return wordList;
	}

}
