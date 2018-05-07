/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年5月7日下午2:31:11</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.core.admin.controller;

import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taolicloud.core.entity.Notice;
import com.taolicloud.core.service.NoticeService;
import com.taolicloud.web.bind.Status;
import com.taolicloud.web.view.RequestElement;

/**
 * @author 马郡
 * @email  Accpect_Majun@163.com / mj@taolicloud.com 
 * @className NoticeController
 * @date   2018年5月7日下午2:31:11
 * @desc  [用一句话描述改文件的功能]
 */
@Controller
@RequestMapping("/admin/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@GetMapping("/list")
	public String list(RequestElement element, Model model) {
		Sort sort = new Sort(Direction.ASC, "id");
		Pageable pageable = new PageRequest(element.getPageNo()-1, element.getPageSize(), sort);
		Page<Notice> notices = noticeService.findAll(pageable);
        int total = notices.getTotalPages();
        int start = element.getPageNo()-3>0?element.getPageNo()-3:1;
        int end = element.getPageNo()+3<total?element.getPageNo()+3:total;
        model.addAttribute("page", notices).addAttribute("start", start).addAttribute("end", end);
		return "admin/notice/list";
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@GetMapping("/add")
	public String add(Model model) {
		List<Status> status = new LinkedList<>();
		status.add(Status.ACTIVED);
		status.add(Status.LOCKED);
		model.addAttribute("status", status);
		return "admin/notice/add";
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@PostMapping("/add")
	public String add(Notice notice) {
		noticeService.saveAndFlush(notice);
		return "redirect:/admin/notice/list";
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable(name="id")int id) {
		Notice notice = noticeService.findById(id);
		if(notice != null) {
			noticeService.delete(notice);
			return "Y";
		}else {
			return "N";
		}
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@PostMapping("/deleteBatch")
	@ResponseBody
	public String noticeBatch(Integer...ids) {
		for (Integer id : ids) {
			Notice notice = noticeService.findById(id);
			if(notice != null) {
				noticeService.delete(notice);
			}
		}
		return "ok";
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name="id")int id, Model model) {
		Notice notice = noticeService.findById(id);
		List<Status> status = new LinkedList<>();
		status.add(Status.ACTIVED);
		status.add(Status.LOCKED);
		model.addAttribute("status", status).addAttribute("notice", notice);
		return "admin/notice/edit";
	}
	
	@RequiresRoles(value = {"ADMIN"}, logical= Logical.OR)
	@PostMapping("/edit")
	public String edit(Notice notice) {
		noticeService.saveAndFlush(notice);
		return "redirect:/admin/notice/list";
	}
}
