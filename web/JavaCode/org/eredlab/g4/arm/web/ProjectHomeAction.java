package org.eredlab.g4.arm.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.eredlab.g4.arm.service.ProjectHomeService;
import org.eredlab.g4.arm.vo.ReplyVo;
import org.eredlab.g4.arm.vo.TopicVo;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.tplengine.FileTemplate;
import org.eredlab.g4.ccl.tplengine.TemplateEngine;
import org.eredlab.g4.ccl.tplengine.TemplateEngineFactory;
import org.eredlab.g4.ccl.tplengine.TemplateType;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.rif.taglib.util.TagHelper;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * 项目主页Action 在线演示系统里面的项目主页相关功能
 * 
 * @author XiongChun
 * @since 2010-10-30
 * @see BaseAction
 */
public class ProjectHomeAction extends BaseAction {

	private ProjectHomeService projectHomeService = (ProjectHomeService) getService("projectHomeService");

	/**
	 * 面板页面初始化
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forumInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("forumView");
	}

	/**
	 * 查询主题列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryTopics(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		qDto.put("locked", "0");
		if (qDto.getAsString("topictype").equals("0")) {
			qDto.remove("topictype");
		}
		List topicList = g4Reader.queryForPage("ProjectHome.queryTopics", qDto);
		Integer count = (Integer) g4Reader.queryForObject("ProjectHome.countTopics", qDto);
		String jsonString = JsonHelper.encodeList2PageJson(topicList, count, GlobalConstants.FORMAT_DateTime);
		write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * 保存新主题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNewTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("content", inDto.getAsString("contentAdd"));
		inDto.put("userid", getSessionContainer(request).getUserInfo().getUserid());
		inDto.put("username", getSessionContainer(request).getUserInfo().getUsername());
		inDto.put("replyable", "1");
		Timestamp timestamp = G4Utils.getCurrentTimestamp();
		inDto.put("addtime", timestamp);
		inDto.put("updatetime", timestamp);
		inDto.put("replycount", 0);
		inDto.put("viewcount", 1);
		inDto.put("locked", 0);
		Dto outDto = projectHomeService.saveTopicItem(inDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "新主题帖发布成功!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 查看主题信息初始化页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward previewTopicInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto dto = new BaseDto();
		Dto qDto = cForm.getParamAsDto(request);
		projectHomeService.updateCount(qDto);
		TopicVo topicVo = (TopicVo) g4Reader.queryForObject("ProjectHome.queryTopicsById", qDto);
		String contentString = topicVo.getContent();
		contentString = contentString.replace("'", "\\'");
		topicVo.setContent2(contentString);
		dto.put("topicVo", topicVo);
		List replyList = g4Reader.queryForList("ProjectHome.queryReplys", qDto);
		for (int i = 0; i < replyList.size(); i++) {
			ReplyVo vo = (ReplyVo) replyList.get(i);
			String replyString = vo.getReplycontent();
			replyString = replyString.replace("'", "\\'");
			vo.setReplycontent2(G4Utils.replace4JsOutput(replyString));
		}
		dto.put("replyList", replyList);
		String flag1 = "0";
		String userid = super.getSessionContainer(request).getUserInfo().getUserid();
		String account = super.getSessionContainer(request).getUserInfo().getAccount();
		if (!account.equalsIgnoreCase("eredlab@vip.qq.com")) {
			if (topicVo.getUserid().equals(userid)) {
				// 隐藏删除
				flag1 = "1";
			} else {
				// 隐藏全部
				flag1 = "2";
			}
		}
		dto.put("flag1", flag1);
		TemplateEngine engine = TemplateEngineFactory.getTemplateEngine(TemplateType.VELOCITY);
		DefaultTemplate template = new FileTemplate();
		template.setTemplateResource(TagHelper.getTemplatePath(getClass().getName(), "TopicDetail.tpl"));
		StringWriter writer = engine.mergeTemplate(template, dto);
		write(writer.toString(), response);
		return mapping.findForward("topicDetailView");
	}

	/**
	 * 回帖数据保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveReplyTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		qDto.put("userid", getSessionContainer(request).getUserInfo().getUserid());
		qDto.put("username", getSessionContainer(request).getUserInfo().getUsername());
		Timestamp timestamp = G4Utils.getCurrentTimestamp();
		qDto.put("replytime", timestamp);
		qDto.put("updatetime", timestamp);
		qDto.put("topicid", qDto.getAsString("topicidReply"));
		qDto.put("replycontent", qDto.getAsString("contentReply"));
		Dto outDto = projectHomeService.saveReplyTopic(qDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "回帖发布成功,感谢您的参与!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 修改主题帖
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		// qDto.put("updatetime", G4Utils.getCurrentTime());
		Dto outDto = projectHomeService.editTopic(qDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "主题帖修改成功!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 删除主题帖
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		Dto outDto = projectHomeService.deleteTopic(qDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "主题帖删除成功!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 修改回帖
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		// qDto.put("updatetime", G4Utils.getCurrentTime());
		Dto outDto = projectHomeService.editReply(qDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "回帖修改成功!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 删除回帖
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto qDto = cForm.getParamAsDto(request);
		Dto outDto = projectHomeService.deleteReply(qDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "回帖删除成功!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
	/**
	 * 插入图片
	 * 
	 * @param
	 * @return
	 */
	public ActionForward doUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto outDto = new BaseDto();
		CommonActionForm cForm = (CommonActionForm) form;
		// 单个文件,如果是多个就cForm.getFile2()....支持最多5个文件
		FormFile myFile = cForm.getFile1();
		// 获取web应用根路径,也可以直接指定服务器任意盘符路径
		String savePath = getServlet().getServletContext().getRealPath("/") + "uploaddata/web/";
		//String savePath = "d:/upload/";
		// 检查路径是否存在,如果不存在则创建之
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String type = myFile.getFileName().substring(myFile.getFileName().lastIndexOf("."));
		// 文件真实文件名
		String fileName = getSessionContainer(request).getUserInfo().getUserid();
		fileName = fileName + "_" + G4Utils.getCurrentTime("yyyyMMddhhmmss") + type;
		// 我们一般会根据某种命名规则对其进行重命名
		// String fileName = ;
		File fileToCreate = new File(savePath, fileName);
		if (myFile.getFileSize() > 102400) {
			outDto.put("success", new Boolean(true));
			outDto.put("msg", "文件上传失败,你只能上传小于100KB的图片文件");
			outDto.put("state", "error");
		}else {
			// 检查同名文件是否存在,不存在则将文件流写入文件磁盘系统
			if (!fileToCreate.exists()) {
				FileOutputStream os = new FileOutputStream(fileToCreate);
				os.write(myFile.getFileData());
				os.flush();
				os.close();
			}
			outDto.put("success", new Boolean(true));
			outDto.put("msg", "文件上传成功");
			outDto.put("state", "ok");
			outDto.put("aUrl", request.getContextPath() + "/uploaddata/web/" + fileName);
		}
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
}
