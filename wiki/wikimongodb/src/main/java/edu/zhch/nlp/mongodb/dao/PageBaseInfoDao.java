package edu.zhch.nlp.mongodb.dao;

import org.springframework.data.mongodb.core.MongoTemplate;

import edu.zhch.nlp.mongodb.model.PageBaseInfo;

public class PageBaseInfoDao extends MongodbBaseDao<PageBaseInfo> {
	
	private MongoTemplate mongoTemplate;

	@Override
	protected Class getEntityClass() {
		return PageBaseInfo.class;
	}

	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
