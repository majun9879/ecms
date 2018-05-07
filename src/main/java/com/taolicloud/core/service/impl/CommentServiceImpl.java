/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年5月1日下午5:27:02</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taolicloud.core.dao.CommentDao;
import com.taolicloud.core.entity.Comment;
import com.taolicloud.core.service.CommentService;

/**
 * @author 马郡
 * @email  Accpect_Majun@163.com / mj@taolicloud.com 
 * @className CommentServiceImpl
 * @date   2018年5月1日下午5:27:02
 * @desc  [用一句话描述改文件的功能]
 */

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	@Override
	public List<Comment> findAll(Sort sort) {
		return commentDao.findAll(sort);
	}

	@Override
	public List<Comment> findAll(Iterable<Integer> ids) {
		return commentDao.findAll(ids);
	}

	@Override
	public Comment findById(Integer id) {
		return commentDao.findOne(id);
	}

	@Override
	public <S extends Comment> List<S> save(Iterable<S> entities) {
		return commentDao.save(entities);
	}

	@Override
	public <S extends Comment> S saveAndFlush(S entity) {
		return commentDao.saveAndFlush(entity);
	}

	@Override
	public <S extends Comment> void delete(S entity) {
		commentDao.delete(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Comment> entities) {
		commentDao.deleteInBatch(entities);
	}

	@Override
	public <S extends Comment> List<S> findAll(Example<S> example) {
		return commentDao.findAll(example);
	}

	@Override
	public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
		return commentDao.findAll(example, sort);
	}

	@Override
	public Page<Comment> findAll(Pageable pageable) {
		return commentDao.findAll(pageable);
	}

	@Override
	public List<Comment> findByQuestion(Integer index) {
		List<Comment> list = commentDao.findAll((root, query, builder) -> {

			List<Order> orders = new ArrayList<>();

			orders.add(builder.desc(root.<Long>get("createTime")));
			
			Predicate predicate = builder.conjunction();

			if (index != null && index > 0) {
				predicate.getExpressions().add(
						builder.equal(root.get("question").as(Integer.class), index));
			}
			
			query.orderBy(orders);
			return predicate;
		});
		
		return list;
	}

}