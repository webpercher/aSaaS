/**
 * 
 */
package com.asaas.test.util;

import java.util.Hashtable;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

/**
 * @author Administrator
 * 
 */
@CsvDataType()
public class Element {
	@CsvField(pos = 1)
	public String pageName;
	
	@CsvField(pos = 2)
	public String name;
	
	@CsvField(pos = 3)
	public String testData;
	
	@CsvField(pos = 4)
	public String xpath;


	/*
	 * testdata format: dataName1<name>dataValue1<data>dataName2<name>dataValue2
	 * Useage: 
	 *   Hashtable userNameDatas = login.UserName.getDatas();
	 *   System.out.println("dataName1: " + userNameDatas.get("dataName1"));
	 */
	public Hashtable<String, String> getDatas() {
		Hashtable<String, String> datas = new Hashtable<String, String>();
		String[] ss = this.testData.split("<data>");
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i].trim();
			if ((s != null) && !s.equals("")) {
				String[] s1 = s.split("<name>");
				datas.put(s1[0].trim(), s1[1].trim());
			}
		}
		return datas;
	}
}
