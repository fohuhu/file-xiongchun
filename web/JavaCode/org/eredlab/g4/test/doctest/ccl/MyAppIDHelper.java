package org.eredlab.g4.test.doctest.ccl;

import org.eredlab.g4.arm.util.idgenerator.IdGenerator;
import org.eredlab.g4.ccl.id.generator.DefaultIDGenerator;

/**
 * 技术白皮书随书代码示例
 * ID生成器
 * 
 * @author XiongChun
 * @since 2011-03-29
 */
public class MyAppIDHelper {
	
	/**
	 * 项目ID
	 */
	private static DefaultIDGenerator defaultIDGenerator_xmid = null;
	
	static {
		IdGenerator idGenerator_xmid = new IdGenerator();
		idGenerator_xmid.setFieldname("XMID");
		defaultIDGenerator_xmid = idGenerator_xmid.getDefaultIDGenerator();
	}
	
	/**
	 * 返回项目ID
	 * 
	 * @return
	 */
	public static String getXmID() {
		return defaultIDGenerator_xmid.create();
	}
}
