/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class DashboardPage implements Page {
    // Declare variable
	public Element menuOnDashboard;
	public Element logout;
	public Element name;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.menuOnDashboard = (Element) elements.get("menuOnDashboard");
		this.logout = (Element) elements.get("logout");
		this.name = (Element) elements.get("name");
	}
}
