/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ClientCompanyDepaList implements Page {
    // Declare variable
	public Element OK;
	public Element subdivisionsOccurJournal;
	public Element departmentKana;
	public Element name;
	public Element subdivisions;
	public Element departmentCode;
	public Element departmentShortName;
	public Element departmentName;
	public Element close;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.OK = (Element) elements.get("OK");
		this.subdivisionsOccurJournal = (Element) elements.get("subdivisionsOccurJournal");
		this.departmentKana = (Element) elements.get("departmentKana");
		this.name = (Element) elements.get("name");
		this.subdivisions = (Element) elements.get("subdivisions");
		this.departmentCode = (Element) elements.get("departmentCode");
		this.departmentShortName = (Element) elements.get("departmentShortName");
		this.departmentName = (Element) elements.get("departmentName");
		this.close = (Element) elements.get("close");
	}
}
