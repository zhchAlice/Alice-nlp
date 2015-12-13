package edu.zhch.nlp.mongodb.service;

import java.util.List;

import javax.annotation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import edu.zhch.nlp.mongodb.dao.WordBaseInfoDao;
import edu.zhch.nlp.mongodb.model.WordBaseInfo;
import edu.zhch.nlp.mongodb.model.WordBaseInfoOfOnePage;

@Service("wordBaseInfoService")
public class WordBaseInfoService {
	
	@SuppressWarnings("restriction")
	@Resource(name="wordBaseInfoDao")
	private WordBaseInfoDao wordBaseInfoDao;
	
	public WordBaseInfo findByName(String wordName){
		Criteria criteria = Criteria.where("wordName").is(wordName);
		Query query = Query.query(criteria);
		return wordBaseInfoDao.findOne(query);
	}
	
	public void insertOneWord(WordBaseInfo wordBaseInfo) {
		wordBaseInfoDao.insert(wordBaseInfo);
	}
	
	public void generateAndinsertFirstEntity(String wordName, String pageId) {
		WordBaseInfo firstWordInfo = new WordBaseInfo(wordName);
		WordBaseInfoOfOnePage firstWordInfoOfOnePage = new WordBaseInfoOfOnePage(pageId);
		firstWordInfoOfOnePage.setWordNumOfThisPage(1);
		firstWordInfo.getWordBaseInfoOfAllPages().add(firstWordInfoOfOnePage);
		wordBaseInfoDao.insert(firstWordInfo);
	}
	
	public void incWordNumOfOnePage(String wordId, String pageId) {
		Criteria criteria = Criteria.where("wordId").is(wordId)
				.and("wordBaseInfoOfAllPages.pageId").is(pageId);
		Query query = Query.query(criteria);
		Update update = new Update().inc("wordBaseInfoOfAllPages.$.wordNumOfThisPage",1);
		wordBaseInfoDao.updateFirst(query, update);
	}
	
	public List<WordBaseInfo> findAllWordsBaseInfo() {
		return wordBaseInfoDao.findAll();
	}
	
	public void updateWholePagesOfThisWord(String wordId,
			List<WordBaseInfoOfOnePage> wordBaseInfoOfAllPages) {
		Criteria criteria = Criteria.where("wordId").is(wordId);
		Query query = Query.query(criteria);
		Update update = Update.update("wordBaseInfoOfAllPages", wordBaseInfoOfAllPages);
		wordBaseInfoDao.update(query, update);
	}
	
	public void updateWordIdf(String wordId, Double wordIdf) {
		Criteria criteria = Criteria.where("wordId").is(wordId);
		Query query = Query.query(criteria);
		Update update = Update.update("wordIdf", wordIdf);				
		wordBaseInfoDao.update(query, update);

	}
	
	public Double getWordIdfValue(String wordId) {
		Criteria criteria = Criteria.where("wordId").is(wordId);
		Query query = Query.query(criteria);
		return wordBaseInfoDao.findOne(query).getWordIdf();
	}
}
