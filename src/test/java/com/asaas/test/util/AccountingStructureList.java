/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class AccountingStructureList implements Page {
    // Declare variable
	public Element menuBar;
	public Element menu_Edit;
	public Element bottomMenu;
	public Element popup_Cancel;
	public Element popup_Select;
	public Element menu_Edit_Undo;
	public Element esc;
	public Element popup_Structure_System;
	public Element popUp;
	public Element popup_Structure_System_List;
	public Element menu_File_CreateStructure;
	public Element menu_File_Logout;
	public Element accountStructureNameList;
	public Element close;
	public Element menu_Edit_Edit;
	public Element menu_Edit_Del;
	public Element menu_Edit_SwitchSelectionToEditMode;
	public Element accountStructureMaintenance;
	public Element name;
	public Element menu_View;
	public Element menu_File_Close;
	public Element del;
	public Element menu_File;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.menuBar = (Element) elements.get("menuBar");
		this.menu_Edit = (Element) elements.get("menu_Edit");
		this.bottomMenu = (Element) elements.get("bottomMenu");
		this.popup_Cancel = (Element) elements.get("popup_Cancel");
		this.popup_Select = (Element) elements.get("popup_Select");
		this.menu_Edit_Undo = (Element) elements.get("menu_Edit_Undo");
		this.esc = (Element) elements.get("esc");
		this.popup_Structure_System = (Element) elements.get("popup_Structure_System");
		this.popUp = (Element) elements.get("popUp");
		this.popup_Structure_System_List = (Element) elements.get("popup_Structure_System_List");
		this.menu_File_CreateStructure = (Element) elements.get("menu_File_CreateStructure");
		this.menu_File_Logout = (Element) elements.get("menu_File_Logout");
		this.accountStructureNameList = (Element) elements.get("accountStructureNameList");
		this.close = (Element) elements.get("close");
		this.menu_Edit_Edit = (Element) elements.get("menu_Edit_Edit");
		this.menu_Edit_Del = (Element) elements.get("menu_Edit_Del");
		this.menu_Edit_SwitchSelectionToEditMode = (Element) elements.get("menu_Edit_SwitchSelectionToEditMode");
		this.accountStructureMaintenance = (Element) elements.get("accountStructureMaintenance");
		this.name = (Element) elements.get("name");
		this.menu_View = (Element) elements.get("menu_View");
		this.menu_File_Close = (Element) elements.get("menu_File_Close");
		this.del = (Element) elements.get("del");
		this.menu_File = (Element) elements.get("menu_File");
	}
}
