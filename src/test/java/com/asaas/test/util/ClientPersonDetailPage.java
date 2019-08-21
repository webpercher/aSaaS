/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ClientPersonDetailPage implements Page {
    // Declare variable
	public Element birthday_year;
	public Element gender_female;
	public Element cityKana;
	public Element save;
	public Element buildingName;
	public Element birthday_yearType;
	public Element clientCode;
	public Element statementType_Estate;
	public Element close;
	public Element birthday_month;
	public Element city;
	public Element localGovernmentCode_disp;
	public Element statementType_General;
	public Element name;
	public Element buildingKanaName;
	public Element streetNumber;
	public Element individualName_lastName;
	public Element prefecture_disp;
	public Element birthday_day;
	public Element occupation;
	public Element streetNumberKana;
	public Element zipCode_search;
	public Element occupation_disp;
	public Element edit;
	public Element zipCode1;
	public Element townKana;
	public Element localGovernmentCode;
	public Element zipCode_disp;
	public Element zipCode2;
	public Element individualName_disp;
	public Element individualName_firstNameKana;
	public Element individualName_lastNameKana;
	public Element city_disp;
	public Element individualName_firstName;
	public Element gender_male;
	public Element prefecture;
	public Element cityKana_disp;
	public Element town;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.birthday_year = (Element) elements.get("birthday_year");
		this.gender_female = (Element) elements.get("gender_female");
		this.cityKana = (Element) elements.get("cityKana");
		this.save = (Element) elements.get("save");
		this.buildingName = (Element) elements.get("buildingName");
		this.birthday_yearType = (Element) elements.get("birthday_yearType");
		this.clientCode = (Element) elements.get("clientCode");
		this.statementType_Estate = (Element) elements.get("statementType_Estate");
		this.close = (Element) elements.get("close");
		this.birthday_month = (Element) elements.get("birthday_month");
		this.city = (Element) elements.get("city");
		this.localGovernmentCode_disp = (Element) elements.get("localGovernmentCode_disp");
		this.statementType_General = (Element) elements.get("statementType_General");
		this.name = (Element) elements.get("name");
		this.buildingKanaName = (Element) elements.get("buildingKanaName");
		this.streetNumber = (Element) elements.get("streetNumber");
		this.individualName_lastName = (Element) elements.get("individualName_lastName");
		this.prefecture_disp = (Element) elements.get("prefecture_disp");
		this.birthday_day = (Element) elements.get("birthday_day");
		this.occupation = (Element) elements.get("occupation");
		this.streetNumberKana = (Element) elements.get("streetNumberKana");
		this.zipCode_search = (Element) elements.get("zipCode_search");
		this.occupation_disp = (Element) elements.get("occupation_disp");
		this.edit = (Element) elements.get("edit");
		this.zipCode1 = (Element) elements.get("zipCode1");
		this.townKana = (Element) elements.get("townKana");
		this.localGovernmentCode = (Element) elements.get("localGovernmentCode");
		this.zipCode_disp = (Element) elements.get("zipCode_disp");
		this.zipCode2 = (Element) elements.get("zipCode2");
		this.individualName_disp = (Element) elements.get("individualName_disp");
		this.individualName_firstNameKana = (Element) elements.get("individualName_firstNameKana");
		this.individualName_lastNameKana = (Element) elements.get("individualName_lastNameKana");
		this.city_disp = (Element) elements.get("city_disp");
		this.individualName_firstName = (Element) elements.get("individualName_firstName");
		this.gender_male = (Element) elements.get("gender_male");
		this.prefecture = (Element) elements.get("prefecture");
		this.cityKana_disp = (Element) elements.get("cityKana_disp");
		this.town = (Element) elements.get("town");
	}
}
