/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年4月30日下午4:56:28</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taolicloud.core.dao.QuestionHistoryDao;
import com.taolicloud.core.entity.Question;
import com.taolicloud.core.entity.QuestionHistory;
import com.taolicloud.core.entity.User;
import com.taolicloud.core.service.QuestionHistoryService;

/**
 * @author 马郡
 * @email  Accpect_Majun@163.com / mj@taolicloud.com 
 * @className QuestionHistoryServiceImpl
 * @date   2018年4月30日下午4:56:28
 * @desc  [用一句话描述改文件的功能]
 */
@Service("questionHist")
public class QuestionHistoryServiceImpl implements QuestionHistoryService{

	@Autowired
	private QuestionHistoryDao historyDao;
	
	@Override
	public List<QuestionHistory> findAll() {
		return historyDao.findAll();
	}

	@Override
	public List<QuestionHistory> findAll(Sort sort) {
		return historyDao.findAll(sort);
	}

	@Override
	public List<QuestionHistory> findAll(Iterable<Integer> ids) {
		return historyDao.findAll(ids);
	}

	@Override
	public QuestionHistory findById(Integer id) {
		return historyDao.findOne(id);
	}

	@Override
	public <S extends QuestionHistory> List<S> save(Iterable<S> entities) {
		return historyDao.save(entities);
	}

	@Override
	public <S extends QuestionHistory> S saveAndFlush(S entity) {
		return historyDao.saveAndFlush(entity);
	}

	@Override
	public <S extends QuestionHistory> void delete(S entity) {
		historyDao.delete(entity);
	}

	@Override
	public void deleteInBatch(Iterable<QuestionHistory> entities) {
		historyDao.deleteInBatch(entities);
	}

	@Override
	public <S extends QuestionHistory> List<S> findAll(Example<S> example) {
		return historyDao.findAll(example);
	}

	@Override
	public <S extends QuestionHistory> List<S> findAll(Example<S> example, Sort sort) {
		return historyDao.findAll(example,sort);
	}

	@Override
	public Page<QuestionHistory> findAll(Pageable pageable) {
		return historyDao.findAll(pageable);
	}

	@Override
	public QuestionHistory findByQuestion(Question question) {
		return historyDao.findByQuestion(question);
	}

	@Override
	public QuestionHistory findByUserAndQuestion(User user, Question question) {
		return historyDao.findByUserAndQuestion(user, question);
	}

	@Override
	public List<QuestionHistory> findByUserAndFlag(User user, boolean flag) {
		return historyDao.findByUserAndFlag(user,flag);
	}

}