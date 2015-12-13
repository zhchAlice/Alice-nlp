package edu.zhch.nlp.mongodb.service;

import java.util.List;

import javax.annotation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import edu.zhch.nlp.mongodb.dao.PageBaseInfoDao;
import edu.zhch.nlp.mongodb.model.OneWeightedWordOfThisPage;
import edu.zhch.nlp.mongodb.model.PageBaseInfo;

@Service("pageBaseInfoService")
public class PageBaseInfoService {
	
	@Resource(name="pageBaseInfoDao")
	private PageBaseInfoDao pageBaseInfoDao;
	
	public void insertOnePage(PageBaseInfo pageBaseInfo) {
		pageBaseInfoDao.insert(pageBaseInfo);
	}
	
	public int getPageTotalWordNum(String pageId) {
		PageBaseInfo pageBaseInfo = pageBaseInfoDao.get(pageId);
		return pageBaseInfo.getPageTotalWordNum();
	}
	public int getAllPagesNum() {
		return pageBaseInfoDao.getCount();
	}
	
	public void addWeightedWordToPage(String pageId, String wordId, double weight) {
		Criteria criteria = Criteria.where("pageId").is(pageId);
		Query query = Query.query(criteria);
		OneWeightedWordOfThisPage oneWeightedWord = 
				new OneWeightedWordOfThisPage(wordId,weight);
		Update update = new Update().addToSet("allWeightedWords", oneWeightedWord);
		pageBaseInfoDao.update(query, update);
	}
	
	public List<PageBaseInfo> findAll() {
		return pageBaseInfoDao.findAll();
	}
	
	public void updatePageConcept(String pageId, String pageConcept) {
		Criteria criteria = Criteria.where("pageId").is(pageId);
		Query query = Query.query(criteria);
		Update update = Update.update("pageConcept", pageConcept);
		pageBaseInfoDao.update(query, update);
	}
	
	public String getPageConcept(String pageId) {
		Criteria criteria = Criteria.where("pageId").is(pageId);
		Query query = Query.query(criteria);
		return pageBaseInfoDao.findOne(query).getPageConcept();
	}
}
