package org.eredlab.g4.demo.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.report.fcf.FcfDataMapper;
import org.eredlab.g4.rif.report.fcf.GraphConfig;
import org.eredlab.g4.rif.report.fcf.Set;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * 演示用 Action
 * 
 * @author XiongChun
 * @since 2010-06-13
 * @see BaseAction
 */
public class TestAction extends BaseAction {

	/**
	 * 测试页面1初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test1Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 实例化一个图形配置对象
		GraphConfig graphConfig = new GraphConfig();
		// 主标题
		graphConfig.setCaption("Google软件2010年月度销售业绩图表");
		// X坐标轴名称
		graphConfig.setXAxisName("月度");
		// 数字值前缀
		graphConfig.setNumberPrefix("$");
		// 使用这种方式可以加入框架没有封装的原生报表属性,原生属可以参考《eRedG4开发指南》的相关章节
		// graphConfig.put("propertyName", "value");
		Dto qDto = new BaseDto();
		qDto.put("product", "1");
		// 查询原始数据
		List list = g4Reader.queryForList("Demo.getFcfDataList", qDto);
		List dataList = new ArrayList();
		// 将原始数据对象转换为框架封装的Set报表数据对象
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto) list.get(i);
			// 实例化一个图表元数据对象
			Set set = new Set();
			set.setName(dto.getAsString("name")); // 名称
			set.setValue(dto.getAsString("value")); // 数据值
			set.setColor(dto.getAsString("color")); // 柱状图颜色
			dataList.add(set);
		}
		// 将图表数据转为Flash能解析的XML资料格式
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		// 此Key对应的<flashReport />标签的datavar属性
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("test1View");
	}

	/**
	 * 测试页面2初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test2Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("ID", "测试");
		return mapping.findForward("test2View");
	}

	/**
	 * 测试3页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test3Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test3View");
	}

	/**
	 * 测试4页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test4Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test4View");
	}

	/**
	 * 测试5页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test5Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test5View");
	}

	/**
	 * 测试6页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test6Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test6View");
	}

	/**
	 * 测试7页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test7Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test7View");
	}

	/**
	 * 测试8页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test8Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test8View");
	}

	/**
	 * 测试9页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test9Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test9View");
	}

	/**
	 * 测试10页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test10Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test10View");
	}

	/**
	 * 测试11页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test11Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test11View");
	}

	/**
	 * 测试12页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test12Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test12View");
	}

	/**
	 * 测试13页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test13Init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward("test13View");
	}

	/**
	 * 上传测试
	 * 
	 * @param
	 * @return
	 */
	public ActionForward doUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Dto outDto = new BaseDto();
		CommonActionForm cForm = (CommonActionForm) form;
		// 单个文件,如果是多个就cForm.getFile2()....支持最多5个文件
		FormFile myFile = cForm.getFile1();
		// 获取web应用根路径,也可以直接指定服务器任意盘符路径
		String savePath = getServlet().getServletContext().getRealPath("/") + "uploaddata/web/";
		// String savePath = "d:/upload/";
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
		} else {
			// 检查同名文件是否存在,不存在则将文件流写入文件磁盘系统
			if (!fileToCreate.exists()) {
				FileOutputStream os = new FileOutputStream(fileToCreate);
				os.write(myFile.getFileData());
				os.flush();
				os.close();
			}
			outDto.put("success", new Boolean(true));
			outDto.put("msg", "文件上传成功");
			outDto.put("aUrl", request.getContextPath() + "/uploaddata/web/" + fileName);
		}
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * 将文件写入文件系统
	 * 
	 * @param formFile
	 * @param fileName
	 * @throws Exception
	 */
	public void saveFileToFileSystem(FormFile formFile) throws Exception {

	}

}
