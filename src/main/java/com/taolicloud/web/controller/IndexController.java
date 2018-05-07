/**
 *  桃李云平台版权声明<br/>
    <center>Copyright (c) 2017 www.taolicloud.com</center> 
 <center> 2018年4月28日下午2:14:20</center>
<center>贵州桃李云科技有限公司拥有本平台的所有资料（包括但不限于文字、图片、音频、视频资料及页面设计、排版、软件等）的版权和/或其他相关知识产权。</center>
<center>未经桃贵州桃李云科技有限公司事先书面许可,对本平台的任何内容、任何单位和个人不得以任何方式复制、转载、链接、转帖、引用、刊登、发表、反编译或者在非桃李云科技所属服务器上做镜像或以其他任何方式使用。</center>
<center>凡侵犯贵州桃李云科技有限公司版权等知识产权的，贵州桃李云科技有限公司将依法追究其法律责任。</center>
 */
package com.taolicloud.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taolicloud.core.entity.Comment;
import com.taolicloud.core.entity.Field;
import com.taolicloud.core.entity.KnowledgePoint;
import com.taolicloud.core.entity.Notice;
import com.taolicloud.core.entity.Page;
import com.taolicloud.core.entity.PageHistory;
import com.taolicloud.core.entity.Question;
import com.taolicloud.core.entity.QuestionHistory;
import com.taolicloud.core.entity.QuestionPage;
import com.taolicloud.core.entity.QuestionType;
import com.taolicloud.core.entity.User;
import com.taolicloud.core.entity.util.Data;
import com.taolicloud.core.entity.util.QuestionImproveResult;
import com.taolicloud.core.service.CommentService;
import com.taolicloud.core.service.FieldService;
import com.taolicloud.core.service.KnowledgePointService;
import com.taolicloud.core.service.NoticeService;
import com.taolicloud.core.service.PageHistoryService;
import com.taolicloud.core.service.PageService;
import com.taolicloud.core.service.QuestionHistoryService;
import com.taolicloud.core.service.QuestionPageService;
import com.taolicloud.core.service.QuestionService;
import com.taolicloud.core.service.QuestionTypeService;
import com.taolicloud.core.service.UserService;
import com.taolicloud.web.bind.Const;

/**
 * @author 马郡
 * @email Accpect_Majun@163.com / mj@taolicloud.com
 * @className IndexController
 * @date 2018年4月28日下午2:14:20
 * @desc [用一句话描述改文件的功能]
 */
@Controller
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private FieldService fieldService;

	@Autowired
	private KnowledgePointService knowledgePointService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionTypeService questionTypeService;

	@Autowired
	private QuestionHistoryService questionHistoryService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private PageService pageService;

	@Autowired
	private QuestionPageService questionPageService;

	@Autowired
	private PageHistoryService pageHistoryService;

	@Autowired
	NoticeService noticeService;

	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {
		List<Notice> notices = noticeService.findByFlag(1);
		model.addAttribute("notices", notices);
		return "index";
	}

	@GetMapping("/setting")
	public String setting(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		} else {
			user = userService.findByUsername(user.getUsername());
			user.setLastLoginTime(new Date());
			userService.upDate(user);
			List<Field> fields = fieldService.findAll();
			model.addAttribute("fields", fields);
			model.addAttribute("user", user);
			return "setting";
		}
	}

	@PostMapping("/setting")
	public String setting(User user) {
		User po = userService.findById(user.getId());
		po.setFieldId(user.getFieldId());
		po.setPhone(user.getPhone());
		po.setEmail(user.getEmail());
		userService.upDate(po);
		return "redirect:/";
	}

	@GetMapping("/practice")
	public String practice() {
		return "practice";
	}

	@GetMapping("/list_page")
	public String list_page(Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "error/login_error";
		} else {
			List<Page> pages = pageService.findByStatus(1);
			model.addAttribute("list", pages);
			return "list_page";
		}
	}

	@GetMapping("/detail_page")
	public String detail_page(Integer id, HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		} else {
			user = userService.findByUsername(user.getUsername());
			Page page = pageService.findById(id);
			List<QuestionPage> questionPages = questionPageService.findByPage(id);
			List<Question> questions = new ArrayList<>();
			for (QuestionPage questionPage : questionPages) {
				questions.add(questionPage.getQuestion());
			}

			PageHistory po = pageHistoryService.findByPageAndUserAndStatus(page, user, true);

			long time = 0;
			if (po != null) {
				time = page.getDuration() * 60 + po.getCreateTime().getTime() - new Date().getTime();
				model.addAttribute("pageHistory", po);

			} else {

				PageHistory pageHistory = new PageHistory();
				pageHistory.setStatus(true);
				pageHistory.setPage(page);
				pageHistory.setUser(user);
				pageHistory.setCreateTime(new Date());
				pageHistoryService.saveAndFlush(pageHistory);

				time = page.getDuration() * 60 + pageHistory.getCreateTime().getTime() - new Date().getTime();
				model.addAttribute("pageHistory", pageHistory);

			}

			model.addAttribute("time", String.valueOf(time));
			model.addAttribute("questions", questions);
			model.addAttribute("page", page);
			return "detail_page";
		}
	}

	@GetMapping("/strengthen")
	public String strengthen(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "error/login_error";
		} else {
			user = userService.findByUsername(user.getUsername());
			if (user.getFieldId() != null && user.getFieldId() > 0) {
				Field field = fieldService.findById(user.getFieldId());
				Set<KnowledgePoint> knowledgePoints = knowledgePointService.getKnowledgePointByField(field);
				LinkedHashMap<String, List<QuestionImproveResult>> list = new LinkedHashMap<>();
				List<QuestionType> qTypes = questionTypeService.findAll();
				for (KnowledgePoint knowledgePoint : knowledgePoints) {
					List<QuestionImproveResult> tmp = new ArrayList<>();
					for (QuestionType questionType : qTypes) {
						List<Question> questions = questionService.findAllByFieldAndKnowledgePointAndQuestionType(field,
								knowledgePoint.getId(), questionType);
						if (questions != null && questions.size() > 0) {
							QuestionImproveResult result = new QuestionImproveResult();
							result.setKnowledgePointId(knowledgePoint.getId());
							result.setKnowledgePointName(knowledgePoint.getName());
							result.setQuestionTypeId(questionType.getId());
							result.setQuestionTypeName(questionType.getName());
							result.setAmount(questions.size());
							Integer wrong = 0, right = 0;
							for (Question question : questions) {
								QuestionHistory questionHistory = questionHistoryService.findByUserAndQuestion(user,
										question);
								if (questionHistory != null) {
									if (questionHistory.getFlag()) {
										right++;
									} else {
										wrong++;
									}
								}
							}
							result.setWrongTimes(wrong);
							result.setRightTimes(right);
							tmp.add(result);
						}
					}
					list.put(knowledgePoint.getName(), tmp);
				}
				model.addAttribute("list", list);
				return "list_knowledge";
			} else {
				return "error/field_unfound";
			}
		}
	}

	@GetMapping("/detail_strengthen")
	public String detailStrengthen(Integer knowledgeId, Integer typeId, Float num, Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}
		user = userService.findByUsername(user.getUsername());

		KnowledgePoint knowledgePoint = knowledgePointService.findById(knowledgeId);
		QuestionType questionType = questionTypeService.findById(typeId);
		List<Question> list = questionService.findAllByFieldAndKnowledgePointAndQuestionType(null, knowledgeId,
				questionType);
		model.addAttribute("knowledges", knowledgePoint);
		model.addAttribute("questionType", questionType);
		model.addAttribute("size", list.size());
		Date time = null;

		Question pre = new Question();
		Question index = new Question();
		Question after = new Question();

		for (int i = 0; i < list.size(); i++) {
			Question question = list.get(i);
			QuestionHistory questionHistory = questionHistoryService.findByUserAndQuestion(user, question);
			if (questionHistory != null) {
				if (time == null) {
					time = questionHistory.getCommitTime();
				} else if (time.compareTo(questionHistory.getCommitTime()) < 0) {
					time = questionHistory.getCommitTime();
				}
				index = question;
				if (i - 1 >= 0) {
					pre = list.get(i - 1);
				}
				if (i + 1 < list.size()) {
					after = list.get(i + 1);
				}
			} else {
				if (index.getId() == null) {
					index = question;
					if (i - 1 >= 0) {
						pre = list.get(i - 1);
					}
					if (i + 1 < list.size()) {
						after = list.get(i + 1);
					}
				}
			}
		}
		List<Comment> comments = commentService.findByQuestion(index.getId());
		float percentage = 0;
		if (num > 0) {
			percentage = num / list.size() * 100;
		}
		model.addAttribute("num", num);
		model.addAttribute("percentage", percentage);
		model.addAttribute("comments", comments);
		model.addAttribute("index", index);
		model.addAttribute("pre", pre);
		model.addAttribute("after", after);

		return "detail_strengthen";
	}

	@GetMapping("/detail_strengthen_order")
	public String order(Integer knowledgeId, Integer typeId, Float num, Integer qId, Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}

		KnowledgePoint knowledgePoint = knowledgePointService.findById(knowledgeId);
		QuestionType questionType = questionTypeService.findById(typeId);
		List<Question> list = questionService.findAllByFieldAndKnowledgePointAndQuestionType(null, knowledgeId,
				questionType);
		model.addAttribute("knowledges", knowledgePoint);
		model.addAttribute("questionType", questionType);
		model.addAttribute("size", list.size());

		Question pre = new Question();
		Question index = new Question();
		Question after = new Question();
		for (int i = 0; i < list.size(); i++) {
			Question question = list.get(i);
			if (question.getId() == qId) {
				index = question;
				if (i - 1 >= 0) {
					pre = list.get(i - 1);
				}
				if (i + 1 < list.size()) {
					after = list.get(i + 1);
				}
			}
		}
		List<Comment> comments = commentService.findByQuestion(index.getId());
		float percentage = 0;
		if (num > 0) {
			percentage = num / list.size() * 100;
		}
		model.addAttribute("num", num);
		model.addAttribute("percentage", percentage);
		model.addAttribute("comments", comments);
		model.addAttribute("index", index);
		model.addAttribute("pre", pre);
		model.addAttribute("after", after);

		return "detail_strengthen";
	}

	@GetMapping("/detail_error")
	public String detail_error(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}
		user = userService.findByUsername(user.getUsername());
		List<QuestionHistory> questionHistories = questionHistoryService.findByUserAndFlag(user, false);
		if (questionHistories != null && questionHistories.size() > 0) {
			Question pre = new Question();
			Question index = new Question();
			Question after = new Question();
			Date tmp = null;
			for (int i = 0; i < questionHistories.size(); i++) {
				QuestionHistory questionHistory = questionHistories.get(i);
				if (tmp == null || tmp.compareTo(questionHistory.getCommitTime()) < 0) {
					index = questionHistory.getQuestion();
					if (i - 1 >= 0) {
						pre = questionHistories.get(i - 1).getQuestion();
					}
					if (i + 1 < questionHistories.size()) {
						after = questionHistories.get(i + 1).getQuestion();
					}
				}
			}
			List<Comment> comments = commentService.findByQuestion(index.getId());
			model.put("size", questionHistories.size());
			model.addAttribute("index", index);
			model.addAttribute("pre", pre);
			model.addAttribute("after", after);
			model.addAttribute("comments", comments);
			return "detail_error";
		}
		return "redirect:/practice";
	}

	@GetMapping("/detail_error_order")
	public String detail_error(Integer id, ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}
		user = userService.findByUsername(user.getUsername());
		List<QuestionHistory> questionHistories = questionHistoryService.findByUserAndFlag(user, false);
		if (questionHistories != null && questionHistories.size() > 0) {
			Question pre = new Question();
			Question index = new Question();
			Question after = new Question();
			for (int i = 0; i < questionHistories.size(); i++) {
				QuestionHistory questionHistory = questionHistories.get(i);
				if (questionHistory.getQuestion().getId() == id) {
					index = questionHistory.getQuestion();
					if (i - 1 >= 0) {
						pre = questionHistories.get(i - 1).getQuestion();
					}
					if (i + 1 < questionHistories.size()) {
						after = questionHistories.get(i + 1).getQuestion();
					}
				}
			}
			List<Comment> comments = commentService.findByQuestion(index.getId());
			model.put("size", questionHistories.size());
			model.addAttribute("index", index);
			model.addAttribute("pre", pre);
			model.addAttribute("after", after);
			model.addAttribute("comments", comments);
			return "detail_error";
		}
		return "redirect:/practice";
	}

	@GetMapping("/submit_question")
	@ResponseBody
	Data submit(Integer qId, String answer, Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		user = userService.findByUsername(user.getUsername());
		Question question = questionService.findById(qId);
		if (answer.equals(question.getAnswer())) {
			QuestionHistory questionHistory = questionHistoryService.findByUserAndQuestion(user, question);
			if (questionHistory == null) {
				questionHistory = new QuestionHistory();
				questionHistory.setUser(user);
				questionHistory.setQuestion(question);
			}
			questionHistory.setCommitTime(new Date());
			questionHistory.setFlag(true);
			questionHistoryService.saveAndFlush(questionHistory);
			return Data.success(Data.NOOP);
		} else {
			QuestionHistory questionHistory = questionHistoryService.findByUserAndQuestion(user, question);
			if (questionHistory == null) {
				questionHistory = new QuestionHistory();
				questionHistory.setUser(user);
				questionHistory.setQuestion(question);
			}
			questionHistory.setCommitTime(new Date());
			questionHistory.setFlag(false);
			questionHistoryService.saveAndFlush(questionHistory);
			return Data.failured("回答错误");
		}
	}

	@GetMapping("/submit_page")
	@ResponseBody
	Data submit_page(PageHistory pageHistory) {

		PageHistory po = pageHistoryService.findById(pageHistory.getId());
		po.setAnswers(pageHistory.getAnswers());
		float counts = 0;

		for (Integer key : po.getAnswers().keySet()) {
			Question question = questionService.findById(key);
			String answer = po.getAnswers().get(key);
			if (answer.equals(question.getAnswer())) {
				QuestionPage questionPage = questionPageService.findByPageAndQuestion(po.getPage(), question);
				counts += questionPage.getPoints();
			}
		}
		po.setCounts(counts);
		po.setStatus(false);
		pageHistoryService.saveAndFlush(po);

		return Data.success(Data.NOOP);
	}

	@GetMapping(value = "/submit_comment")
	@ResponseBody
	Data submit_comment(String content, Integer qId, Integer uId) {

		User user = userService.findById(uId);
		if (user == null) {
			return Data.failured("未登录");
		}

		Comment comment = new Comment();
		User po = new User();
		po.setId(user.getId());
		po.setUsername(user.getUsername());

		comment.setUser(user);
		comment.setQuestion(qId);
		comment.setCreateTime(new Date());
		comment.setContent(content);

		commentService.saveAndFlush(comment);

		comment.setUser(po);
		return Data.success(comment);
	}

	@GetMapping("/user_page")
	public String user_page(Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}
		user = userService.findByUsername(user.getUsername());
		List<PageHistory> pageHistories = pageHistoryService.findAllByPageAndUserAndStatus(null, user, false);

		model.addAttribute("list", pageHistories);
		return "user_page";
	}

	@GetMapping("/user_question")
	public String user_question(Model model, HttpSession session) {
		User user = (User) session.getAttribute(Const.LOGIN_ADMIN);
		if (user == null) {
			return "redirect:/admin/login";
		}
		user = userService.findByUsername(user.getUsername());
		if (user.getFieldId() != null && user.getFieldId() > 0) {
			Field field = fieldService.findById(user.getFieldId());
			Set<KnowledgePoint> knowledgePoints = knowledgePointService.getKnowledgePointByField(field);
			LinkedHashMap<String, List<QuestionImproveResult>> list = new LinkedHashMap<>();
			LinkedHashMap<String, Float> points = new LinkedHashMap<>();
			List<QuestionType> qTypes = questionTypeService.findAll();
			for (KnowledgePoint knowledgePoint : knowledgePoints) {
				List<QuestionImproveResult> tmp = new ArrayList<>();
				float sum = 0;
				float sure = 0;
				for (QuestionType questionType : qTypes) {
					List<Question> questions = questionService.findAllByFieldAndKnowledgePointAndQuestionType(field,
							knowledgePoint.getId(), questionType);
					if (questions != null && questions.size() > 0) {
						QuestionImproveResult result = new QuestionImproveResult();
						result.setKnowledgePointId(knowledgePoint.getId());
						result.setKnowledgePointName(knowledgePoint.getName());
						result.setQuestionTypeId(questionType.getId());
						result.setQuestionTypeName(questionType.getName());
						result.setAmount(questions.size());
						Integer wrong = 0, right = 0;
						for (Question question : questions) {
							QuestionHistory questionHistory = questionHistoryService.findByUserAndQuestion(user,
									question);
							if (questionHistory != null) {
								if (questionHistory.getFlag()) {
									right++;
								} else {
									wrong++;
								}
							}
						}
						result.setWrongTimes(wrong);
						result.setRightTimes(right);
						tmp.add(result);
						sum += questions.size();
						sure += right;
					}
				}
				list.put(knowledgePoint.getName(), tmp);
				if (sum != 0 && sure != 0) {
					points.put(knowledgePoint.getName(), sure / sum * 100);
				} else {
					points.put(knowledgePoint.getName(), (float) 0);
				}
			}
			model.addAttribute("points", points);
			model.addAttribute("list", list);
			return "uesr_question";
		} else {
			return "error/field_unfound";
		}
	}
}