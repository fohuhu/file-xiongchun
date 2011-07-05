package org.eredlab.g4.arm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.arm.service.PartService;
import org.eredlab.g4.rif.web.BaseAction;

/**
 * UI组件角色授权
 * 
 * @author XiongChun
 * @since 2011-06-03
 * @see BaseAction
 */
public class RolePartAction extends BaseAction {
	
	private PartService partService = (PartService) getService("partService");

	/**
	 * 页面初始化
	 * 
	 * @param
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("initView");
	}
}
