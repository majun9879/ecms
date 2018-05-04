/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年3月21日上午9:50:05</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.core.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 马郡
 * @email Accpect_Majun@163.com / mj@taolicloud.com
 * @className Group
 * @date 2018年3月21日上午9:50:05
 * @desc [年级组实体类]
 */
@Entity
@Table(name = "ECMS_GROUP")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1339992415732002527L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "group_level_id")
	private int groupLevelId;

	@Column(name = "parent")
	private int parent;
	
	@JsonIgnore
	@OneToMany(mappedBy = "group", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<Question> questions;

	public Group() {
		super();
	}

	public Group(int id, String name, int groupLevelId, int parent, Set<Question> questions) {
		super();
		this.id = id;
		this.name = name;
		this.groupLevelId = groupLevelId;
		this.parent = parent;
		this.questions = questions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupLevelId() {
		return groupLevelId;
	}

	public void setGroupLevelId(int groupLevelId) {
		this.groupLevelId = groupLevelId;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

}
