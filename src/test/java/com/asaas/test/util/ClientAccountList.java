/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ClientAccountList implements Page {
    // Declare variable
	public Element shortAccountName_cashItem;
	public Element fixedAsset_line_shortName;
	public Element fixedAsset;
	public Element code_cashItem_dis;
	public Element subjectNameAbbreviation2;
	public Element fixedAsset_line_kanaName;
	public Element close;
	public Element shortAccountName_cashItem_dis;
	public Element fixedAsset_line_code;
	public Element name;
	public Element fixTpye_cashItem_dis;
	public Element cashItem_select;
	public Element code_cashItem;
	public Element fixTpye_cashItem;
	public Element cashItem;
	public Element fixTpye;
	public Element taxRate;
	public Element newSubject;
	public Element code;
	public Element kanaName_cashItem_dis;
	public Element officialAccountName_cashItem_dis;
	public Element officialAccountName;
	public Element lcashItem;
	public Element fixedAsset_line;
	public Element kanaName;
	public Element fixedAsset_line_next;
	public Element taxType_cashItem_dis;
	public Element clientAccountCode2;
	public Element clientAccountCode1;
	public Element fixedAsset_line_name;
	public Element cashItem_input;
	public Element taxType;
	public Element officialAccountName_cashItem;
	public Element debitType;
	public Element shortAccountName;
	public Element kanaName_cashItem;
	public Element taxType_cashItem;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.shortAccountName_cashItem = (Element) elements.get("shortAccountName_cashItem");
		this.fixedAsset_line_shortName = (Element) elements.get("fixedAsset_line_shortName");
		this.fixedAsset = (Element) elements.get("fixedAsset");
		this.code_cashItem_dis = (Element) elements.get("code_cashItem_dis");
		this.subjectNameAbbreviation2 = (Element) elements.get("subjectNameAbbreviation2");
		this.fixedAsset_line_kanaName = (Element) elements.get("fixedAsset_line_kanaName");
		this.close = (Element) elements.get("close");
		this.shortAccountName_cashItem_dis = (Element) elements.get("shortAccountName_cashItem_dis");
		this.fixedAsset_line_code = (Element) elements.get("fixedAsset_line_code");
		this.name = (Element) elements.get("name");
		this.fixTpye_cashItem_dis = (Element) elements.get("fixTpye_cashItem_dis");
		this.cashItem_select = (Element) elements.get("cashItem_select");
		this.code_cashItem = (Element) elements.get("code_cashItem");
		this.fixTpye_cashItem = (Element) elements.get("fixTpye_cashItem");
		this.cashItem = (Element) elements.get("cashItem");
		this.fixTpye = (Element) elements.get("fixTpye");
		this.taxRate = (Element) elements.get("taxRate");
		this.newSubject = (Element) elements.get("newSubject");
		this.code = (Element) elements.get("code");
		this.kanaName_cashItem_dis = (Element) elements.get("kanaName_cashItem_dis");
		this.officialAccountName_cashItem_dis = (Element) elements.get("officialAccountName_cashItem_dis");
		this.officialAccountName = (Element) elements.get("officialAccountName");
		this.lcashItem = (Element) elements.get("lcashItem");
		this.fixedAsset_line = (Element) elements.get("fixedAsset_line");
		this.kanaName = (Element) elements.get("kanaName");
		this.fixedAsset_line_next = (Element) elements.get("fixedAsset_line_next");
		this.taxType_cashItem_dis = (Element) elements.get("taxType_cashItem_dis");
		this.clientAccountCode2 = (Element) elements.get("clientAccountCode2");
		this.clientAccountCode1 = (Element) elements.get("clientAccountCode1");
		this.fixedAsset_line_name = (Element) elements.get("fixedAsset_line_name");
		this.cashItem_input = (Element) elements.get("cashItem_input");
		this.taxType = (Element) elements.get("taxType");
		this.officialAccountName_cashItem = (Element) elements.get("officialAccountName_cashItem");
		this.debitType = (Element) elements.get("debitType");
		this.shortAccountName = (Element) elements.get("shortAccountName");
		this.kanaName_cashItem = (Element) elements.get("kanaName_cashItem");
		this.taxType_cashItem = (Element) elements.get("taxType_cashItem");
	}
}
