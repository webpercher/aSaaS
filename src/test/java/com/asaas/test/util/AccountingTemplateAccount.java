/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class AccountingTemplateAccount implements Page {
    // Declare variable
	public Element popup_Cancel;
	public Element menu_Edit_Undo;
	public Element popUp;
	public Element menu_File_Logout;
	public Element msg_Ok;
	public Element menu_Edit_Edit;
	public Element menu_File_NewAccount;
	public Element menu_Edit_Del;
	public Element popup_Ok;
	public Element name;
	public Element menu_View;
	public Element menu_File_StopEditModeAndPublish;
	public Element menu_Edit_MoveToUp;
	public Element menu_Edit_Cancel;
	public Element menu_Edit;
	public Element menuBar;
	public Element menu_Edit_Restore;
	public Element menu_File_Print;
	public Element popup_AccountStructureName;
	public Element msg_Cancel;
	public Element msg;
	public Element menu_File_SaveWithNewName;
	public Element menu_Edit_MoveToDown;
	public Element currentAssets;
	public Element menu_File_SaveAndClose;
	public Element menu_File;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.popup_Cancel = (Element) elements.get("popup_Cancel");
		this.menu_Edit_Undo = (Element) elements.get("menu_Edit_Undo");
		this.popUp = (Element) elements.get("popUp");
		this.menu_File_Logout = (Element) elements.get("menu_File_Logout");
		this.msg_Ok = (Element) elements.get("msg_Ok");
		this.menu_Edit_Edit = (Element) elements.get("menu_Edit_Edit");
		this.menu_File_NewAccount = (Element) elements.get("menu_File_NewAccount");
		this.menu_Edit_Del = (Element) elements.get("menu_Edit_Del");
		this.popup_Ok = (Element) elements.get("popup_Ok");
		this.name = (Element) elements.get("name");
		this.menu_View = (Element) elements.get("menu_View");
		this.menu_File_StopEditModeAndPublish = (Element) elements.get("menu_File_StopEditModeAndPublish");
		this.menu_Edit_MoveToUp = (Element) elements.get("menu_Edit_MoveToUp");
		this.menu_Edit_Cancel = (Element) elements.get("menu_Edit_Cancel");
		this.menu_Edit = (Element) elements.get("menu_Edit");
		this.menuBar = (Element) elements.get("menuBar");
		this.menu_Edit_Restore = (Element) elements.get("menu_Edit_Restore");
		this.menu_File_Print = (Element) elements.get("menu_File_Print");
		this.popup_AccountStructureName = (Element) elements.get("popup_AccountStructureName");
		this.msg_Cancel = (Element) elements.get("msg_Cancel");
		this.msg = (Element) elements.get("msg");
		this.menu_File_SaveWithNewName = (Element) elements.get("menu_File_SaveWithNewName");
		this.menu_Edit_MoveToDown = (Element) elements.get("menu_Edit_MoveToDown");
		this.currentAssets = (Element) elements.get("currentAssets");
		this.menu_File_SaveAndClose = (Element) elements.get("menu_File_SaveAndClose");
		this.menu_File = (Element) elements.get("menu_File");
	}
}
