/**
 * 
 */
package com.asaas.test.util;

import java.io.File;
import java.util.HashMap;

/**
 * @author Administrator
 * 
 */
public class TestHelper {
	public static CSVProcessor cproc = new CSVProcessor();
	public static final String pageFilePath = new File(System.getProperty(
			"testDataDirPath", "resources/pages/testdata/"),
			System.getProperty("testDataLocale", "ja-JP")).toString();
	public static final String csv = ".csv";
	public static final String pagePackage = System.getProperty("pagePackage",
			"com.asaas.test.util").trim()
			+ ".";
	public static final String testDataEncoding = System.getProperty(
			"testDataEncoding", "UTF-8");

	public static HashMap<String, Object> getTestData(String inFileName) {
		return cproc.deserialize(inFileName, testDataEncoding);
	}

	public Page getPage(String pageName) {
		Page page = null;
		try {
			page = (Page) Class.forName(pagePackage + pageName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.init(getTestData(pageFilePath + File.separator + pageName + csv));
		return page;
	}
}
