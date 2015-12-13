package edu.zhch.nlp.mongodb.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import edu.zhch.nlp.mongodb.model.WordBaseInfo;

@Service("wordBaseInfoDao")
public class WordBaseInfoDao extends MongodbBaseDao<WordBaseInfo> {
	
	private MongoTemplate mongoTemplate;

	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return WordBaseInfo.class;
	}

	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		
	}

}
