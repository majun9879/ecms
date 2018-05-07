/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年5月7日下午2:24:15</center>
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

import com.taolicloud.core.dao.NoticeDao;
import com.taolicloud.core.entity.Notice;
import com.taolicloud.core.service.NoticeService;

/**
 * @author 马郡
 * @email Accpect_Majun@163.com / mj@taolicloud.com
 * @className NoticeServiceImpl
 * @date 2018年5月7日下午2:24:15
 * @desc [用一句话描述改文件的功能]
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public List<Notice> findAll() {
		return noticeDao.findAll();
	}

	@Override
	public List<Notice> findAll(Sort sort) {
		return noticeDao.findAll(sort);
	}

	@Override
	public List<Notice> findAll(Iterable<Integer> ids) {
		return noticeDao.findAll(ids);
	}

	@Override
	public Notice findById(Integer id) {
		return noticeDao.findOne(id);
	}

	@Override
	public <S extends Notice> List<S> save(Iterable<S> entities) {
		return noticeDao.save(entities);
	}

	@Override
	public <S extends Notice> S saveAndFlush(S entity) {
		return noticeDao.saveAndFlush(entity);
	}

	@Override
	public <S extends Notice> void delete(S entity) {
		noticeDao.delete(entity);
	}

	@Override
	public void deleteInBatch(Iterable<Notice> entities) {
		noticeDao.deleteInBatch(entities);
	}

	@Override
	public <S extends Notice> List<S> findAll(Example<S> example) {
		return noticeDao.findAll(example);
	}

	@Override
	public <S extends Notice> List<S> findAll(Example<S> example, Sort sort) {
		return noticeDao.findAll(example, sort);
	}

	@Override
	public Page<Notice> findAll(Pageable pageable) {
		return noticeDao.findAll(pageable);
	}

	@Override
	public List<Notice> findByFlag(Integer flag) {
		return noticeDao.findByStatus(flag);
	}

}