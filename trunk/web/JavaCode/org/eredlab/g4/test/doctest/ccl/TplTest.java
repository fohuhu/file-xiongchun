package org.eredlab.g4.test.doctest.ccl;

import java.io.StringWriter;
import java.util.List;

import org.eredlab.g4.bmf.base.IDao;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.tplengine.DefaultTemplate;
import org.eredlab.g4.ccl.tplengine.FileTemplate;
import org.eredlab.g4.ccl.tplengine.StringTemplate;
import org.eredlab.g4.ccl.tplengine.TemplateEngine;
import org.eredlab.g4.ccl.tplengine.TemplateEngineFactory;
import org.eredlab.g4.ccl.tplengine.TemplateType;

public class TplTest {

	/**
	 * 技术白皮书随书代码示例
	 * 模板引擎
	 * @author XiongChun
	 * @since 2011-03-29
	 */
	public static void main(String[] args) {
		//testTplFIle();
		testTplString();
	}
	
	/**
	 * 文件模版
	 */
	private static void testTplFIle(){
		IDao dao = (IDao)SpringBeanLoader.getSpringBean("g4Dao");
		//List为UserVo对象的集合
		List userList = dao.queryForList("Demo.queryUserInfo4Doc");
		Dto dto = new BaseDto();
		dto.put("userList", userList);
		dto.put("title", "UserInfo List");
		TemplateEngine engine = TemplateEngineFactory.
		getTemplateEngine(TemplateType.VELOCITY);
		DefaultTemplate template = new FileTemplate();
		template.setTemplateResource("org/eredlab/g4/test/doctest/ccl/test.tpl");
		StringWriter writer = engine.mergeTemplate(template, dto);
		System.out.println(writer.toString());
	}
	
	/**
	 * 字符串模版
	 */
	private static void testTplString(){
		IDao dao = (IDao)SpringBeanLoader.getSpringBean("g4Dao");
		//List为UserVo对象的集合
		List userList = dao.queryForList("Demo.queryUserInfo4Doc");
		Dto dto = new BaseDto();
		dto.put("userList", userList);
		dto.put("title", "UserInfo List");
		String tpl = "${title}:{#foreach($user in $userList)[userid:${user.userid}, username:${user.username}]#end}";
		TemplateEngine engine = TemplateEngineFactory.
		getTemplateEngine(TemplateType.VELOCITY);
		DefaultTemplate template = new StringTemplate(tpl);
		StringWriter writer = engine.mergeTemplate(template, dto);
		System.out.println(writer.toString());
	}

}
