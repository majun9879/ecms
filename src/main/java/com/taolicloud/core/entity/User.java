/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年3月16日上午9:37:30</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 马郡
 * @email Accpect_Majun@163.com / mj@taolicloud.com
 * @className User
 * @date 2018年3月16日上午9:37:30
 * @desc [用户实体类]
 */
@Entity
@Table(name = "ECMS_USER")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2224567088082091306L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "salt")
	private String salt;

	@Column(name = "realname")
	private String realname;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "status")
	private Integer status;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "field_id")
	private Integer fieldId;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "type_id")
	private Integer typeId;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "last_login_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	@Column(name = "province")
	private String province;

	@Column(name = "company")
	private String company;

	@Column(name = "department")
	private String department;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<QuestionHistory> questionHistories = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<Comment> comment = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<PageHistory> pageHistories = new HashSet<>();

	public User() {
		super();
	}

	public User(Integer id, String username, String password, String salt, String realname, String email, String phone,
			Integer status, Date createTime, Integer fieldId, String fieldName, Integer typeId, String typeName,
			Date lastLoginTime, String province, String company, String department,
			Set<QuestionHistory> questionHistories, Set<Comment> comment, Set<PageHistory> pageHistories) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.realname = realname;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.createTime = createTime;
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.typeId = typeId;
		this.typeName = typeName;
		this.lastLoginTime = lastLoginTime;
		this.province = province;
		this.company = company;
		this.department = department;
		this.questionHistories = questionHistories;
		this.comment = comment;
		this.pageHistories = pageHistories;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCredentialsSalt() {
		return username + salt;
	}

	public Set<QuestionHistory> getQuestionHistories() {
		return questionHistories;
	}

	public void setQuestionHistories(Set<QuestionHistory> questionHistories) {
		this.questionHistories = questionHistories;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public Set<PageHistory> getPageHistories() {
		return pageHistories;
	}

	public void setPageHistories(Set<PageHistory> pageHistories) {
		this.pageHistories = pageHistories;
	}

}