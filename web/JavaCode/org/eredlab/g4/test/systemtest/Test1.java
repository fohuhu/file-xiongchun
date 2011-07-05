package org.eredlab.g4.test.systemtest;

import java.math.BigDecimal;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIGlobalBinding;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 0, b=10;
		System.out.println( new BigDecimal(a + Math.random() * b).intValue());
	}

}
