package org.eredlab.g4.arm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.PartService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * UI组件托管
 * 
 * @author XiongChun
 * @since 2011-06-03
 * @see BaseAction
 */
public class PartAction extends BaseAction {
	
	private PartService service = (PartService)getService("partService");

	/**
	 * 页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto dto = (Dto)g4Reader.queryForObject("Resource.queryEamenuByMenuID", "01");
		request.setAttribute("rootMenuName", dto.getAsString("menuname"));
		return mapping.findForward("initView");
	}
	
	/**
	 * 查询菜单项目 生成菜单树
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryMenuItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto dto = new BaseDto();
		String nodeid = request.getParameter("node");
		dto.put("parentid", nodeid);
		dto.put("menutype", ArmConstants.MENUTYPE_BUSINESS);
		List menuList = g4Reader.queryForList("Resource.queryMenuItemsByDto", dto);
		Dto menuDto = new BaseDto();
		for (int i = 0; i < menuList.size(); i++) {
			menuDto = (BaseDto) menuList.get(i);
			if (menuDto.getAsString("leaf").equals(ArmConstants.LEAF_Y))
				menuDto.put("leaf", new Boolean(true));
			else
				menuDto.put("leaf", new Boolean(false));
			if (menuDto.getAsString("id").length() == 4)
				menuDto.put("expanded", new Boolean(true));
		}
		write(JsonHelper.encodeObject2Json(menuList), response);
		return mapping.findForward(null);
	}
	
	/**
	 * 查询UI组件列表
	 * 
	 * @param
	 * @return
	 */
	public ActionForward queryParts(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm)form;
		Dto dto = cForm.getParamAsDto(request);
		List list = g4Reader.queryForPage("Part.queryParts", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("Part.queryPartsForPageCount", dto);
		String jsonString = encodeList2PageJson(list, countInteger, null);
		write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * 保存脏数据
	 * 
	 * @param
	 * @return
	 */
	public ActionForward saveDirtyDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		List list  = aForm.getGridDirtyData(request);
		Dto inDto = new BaseDto();
		inDto.setDefaultAList(list);
		Dto outDto = service.saveDirtyDatas(inDto);
		if (outDto.getSuccess()) {
			setOkTipMsg("数据保存成功", response);
		}else {
			setOkTipMsg("保存操作被取消,同一托管页面上元素Dom标志只能唯一,请检查", response);
		}
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
	/**
	 * 删除数据
	 * 
	 * @param
	 * @return
	 */
	public ActionForward deleteItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		service.deleteItem(inDto);
		setOkTipMsg("数据删除成功", response);
		return mapping.findForward(null);
	}
	
}
