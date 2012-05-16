package org.eredlab.g4.test.systemtest;

import java.util.List;

import org.eredlab.g4.bmf.base.IReader;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IReader g4Reader = (IReader) SpringBeanLoader.getSpringBean("g4Reader");
		Dto inDto = new BaseDto();
		inDto.put("xb", "xb");
		inDto.put("rq", "2009-09-04");
		List list = g4Reader.queryForList("Demo.testDynamicGroupField1", inDto);
		System.out.println(list.size());
	}
}
