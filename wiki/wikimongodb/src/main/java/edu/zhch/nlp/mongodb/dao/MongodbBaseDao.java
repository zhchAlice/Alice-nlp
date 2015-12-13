package edu.zhch.nlp.mongodb.dao;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class MongodbBaseDao<T>{  
	  
    /** 
     * spring mongodb�����ɲ����ࡡ 
     */  
    protected MongoTemplate mongoTemplate;  
  
    /** 
     * ͨ��������ѯʵ��(����) 
     *  
     * @param query 
     */  
    public List find(Query query) {  
        return mongoTemplate.find(query, this.getEntityClass());  
    } 
    
    public int getCount(){
    	return mongoTemplate.findAll(this.getEntityClass()).size();
    }
  
    /** 
     * ͨ��һ����������ѯһ��ʵ�� 
     *  
     * @param query 
     * @return 
     */  
    public T findOne(Query query) {  
        return (T) mongoTemplate.findOne(query, this.getEntityClass());  
    }  
    
    public List<T> findAll(){
    	return mongoTemplate.findAll(this.getEntityClass());
    }
  
    /** 
     * ͨ��������ѯ�������� 
     *  
     * @param query 
     * @param update 
     * @return 
     */  
    public void update(Query query, Update update) {  
        mongoTemplate.upsert(query, update, this.getEntityClass());  
    } 
    
    public void updateFirst(Query query, Update update) {
    	mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }
  
    /** 
     * ����һ������mongodb 
     *  
     * @param bean 
     * @return 
     */  
    public T save(T bean) {  
        mongoTemplate.save(bean);  
        return bean;  
    } 
    
    public void insert(T bean) {
    	mongoTemplate.insert(bean);
    }
  
    /** 
     * ͨ��ID��ȡ��¼ 
     *  
     * @param id 
     * @return 
     */  
    public T get(String id) {  
        return (T) mongoTemplate.findById(id, this.getEntityClass());  
    }  
  
    /** 
     * ͨ��ID��ȡ��¼,����ָ���˼�����(�����˼) 
     *  
     * @param id 
     * @param collectionName 
     *            ������ 
     * @return 
     */  
    public T get(String id, String collectionName) {  
        return (T) mongoTemplate.findById(id, this.getEntityClass(), collectionName);  
    }  
  
    /** 
     * ��ȡ��Ҫ������ʵ����class 
     *  
     * @return 
     */  
    protected abstract Class getEntityClass();  
  
    /** 
     * ע��mongodbTemplate 
     *  
     * @param mongoTemplate 
     */  
    protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);  
}  
