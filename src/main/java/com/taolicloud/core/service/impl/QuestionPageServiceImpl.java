/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年4月26日下午2:59:12</center>
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

import com.taolicloud.core.dao.QuestionPageDao;
import com.taolicloud.core.entity.Question;
import com.taolicloud.core.entity.QuestionPage;
import com.taolicloud.core.service.PageService;
import com.taolicloud.core.service.QuestionPageService;

/**
 * @author 马郡
 * @email Accpect_Majun@163.com / mj@taolicloud.com
 * @className QuestionPageServiceImpl
 * @date 2018年4月26日下午2:59:12
 * @desc [用一句话描述改文件的功能]
 */
@Service("questionPage")
public class QuestionPageServiceImpl implements QuestionPageService {

	@Autowired
	private QuestionPageDao questionPageDao;

	@Autowired
	private PageService pageService;
	
	@Override
	public List<QuestionPage> findAll() {
		return questionPageDao.findAll();
	}

	@Override
	public List<QuestionPage> findAll(Sort sort) {
		return questionPageDao.findAll(sort);
	}

	@Override
	public List<QuestionPage> findAll(Iterable<Integer> ids) {
		return questionPageDao.findAll(ids);
	}

	@Override
	public QuestionPage findById(Integer id) {
		return questionPageDao.findOne(id);
	}

	@Override
	public <S extends QuestionPage> List<S> save(Iterable<S> entities) {
		return questionPageDao.save(entities);
	}

	@Override
	public <S extends QuestionPage> S saveAndFlush(S entity) {
		QuestionPage questionPage = questionPageDao.findByPageAndQuestion(entity.getPage(), entity.getQuestion());
		if (questionPage == null) {
			return questionPageDao.saveAndFlush(entity);
		} else {
			return null;
		}
	}
	
	@Override
	public void updata(QuestionPage po) {
		questionPageDao.save(po);
	}

	@Override
	public <S extends QuestionPage> void delete(S entity) {
		questionPageDao.delete(entity);
	}

	@Override
	public void deleteInBatch(Iterable<QuestionPage> entities) {
		questionPageDao.delete(entities);
	}

	@Override
	public <S extends QuestionPage> List<S> findAll(Example<S> example) {
		return questionPageDao.findAll(example);
	}

	@Override
	public <S extends QuestionPage> List<S> findAll(Example<S> example, Sort sort) {
		return questionPageDao.findAll(example, sort);
	}

	@Override
	public Page<QuestionPage> findAll(Pageable pageable) {
		return questionPageDao.findAll(pageable);
	}

	@Override
	public List<QuestionPage> findByPage(Integer pid) {
		com.taolicloud.core.entity.Page page = pageService.findById(pid);
		return questionPageDao.findByPage(page);
	}

	@Override
	public QuestionPage findByPageAndQuestion(com.taolicloud.core.entity.Page page, Question question) {
		return questionPageDao.findByPageAndQuestion(page,question);
	}

}