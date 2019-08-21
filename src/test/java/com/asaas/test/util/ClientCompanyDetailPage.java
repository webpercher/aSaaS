/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ClientCompanyDetailPage implements Page {
    // Declare variable
	public Element cityKana;
	public Element save;
	public Element buildingName;
	public Element establishment_day;
	public Element amountOfFoundsAndOther;
	public Element representativeNameKana_2;
	public Element close;
	public Element city;
	public Element accountingMangerNameKana_1;
	public Element localGovernmentCode_disp;
	public Element establishment_month;
	public Element accountingMangerNameKana_2;
	public Element okForDel;
	public Element address_disp;
	public Element buildingKanaName;
	public Element establishment_year;
	public Element endingDate;
	public Element accountingInChargeNameKana_1;
	public Element accountingInChargeNameKana_2;
	public Element accountingInChargeName_1;
	public Element accountingInChargeName_2;
	public Element zipCode1;
	public Element companyName;
	public Element townKana;
	public Element representativeName_firstName;
	public Element zipCode2;
	public Element accountingMangerName_1;
	public Element accountingMangerName_2;
	public Element businessContents;
	public Element businessItem;
	public Element Email;
	public Element delButton;
	public Element town;
	public Element amountOfFounds;
	public Element companyShortName;
	public Element representativeName_lastName;
	public Element companyKana;
	public Element addressKana_disp;
	public Element representativeName_lastNameKana;
	public Element name;
	public Element representativeNameKaana_1;
	public Element streetNumber;
	public Element URL;
	public Element representativeName_firstNameKana;
	public Element endingMonth;
	public Element streetNumberKana;
	public Element telephoneNum_1;
	public Element representativeName_1;
	public Element zipCode_search;
	public Element telephoneNum_2;
	public Element representativeName_2;
	public Element telephoneNum_3;
	public Element edit;
	public Element localGovernmentCode;
	public Element zipCode_disp;
	public Element companyCode;
	public Element prefecture;
	public Element cellNum_2;
	public Element representativeName_disp;
	public Element cellNum_1;
	public Element okForRemove;
	public Element cellNum_3;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.cityKana = (Element) elements.get("cityKana");
		this.save = (Element) elements.get("save");
		this.buildingName = (Element) elements.get("buildingName");
		this.establishment_day = (Element) elements.get("establishment_day");
		this.amountOfFoundsAndOther = (Element) elements.get("amountOfFoundsAndOther");
		this.representativeNameKana_2 = (Element) elements.get("representativeNameKana_2");
		this.close = (Element) elements.get("close");
		this.city = (Element) elements.get("city");
		this.accountingMangerNameKana_1 = (Element) elements.get("accountingMangerNameKana_1");
		this.localGovernmentCode_disp = (Element) elements.get("localGovernmentCode_disp");
		this.establishment_month = (Element) elements.get("establishment_month");
		this.accountingMangerNameKana_2 = (Element) elements.get("accountingMangerNameKana_2");
		this.okForDel = (Element) elements.get("okForDel");
		this.address_disp = (Element) elements.get("address_disp");
		this.buildingKanaName = (Element) elements.get("buildingKanaName");
		this.establishment_year = (Element) elements.get("establishment_year");
		this.endingDate = (Element) elements.get("endingDate");
		this.accountingInChargeNameKana_1 = (Element) elements.get("accountingInChargeNameKana_1");
		this.accountingInChargeNameKana_2 = (Element) elements.get("accountingInChargeNameKana_2");
		this.accountingInChargeName_1 = (Element) elements.get("accountingInChargeName_1");
		this.accountingInChargeName_2 = (Element) elements.get("accountingInChargeName_2");
		this.zipCode1 = (Element) elements.get("zipCode1");
		this.companyName = (Element) elements.get("companyName");
		this.townKana = (Element) elements.get("townKana");
		this.representativeName_firstName = (Element) elements.get("representativeName_firstName");
		this.zipCode2 = (Element) elements.get("zipCode2");
		this.accountingMangerName_1 = (Element) elements.get("accountingMangerName_1");
		this.accountingMangerName_2 = (Element) elements.get("accountingMangerName_2");
		this.businessContents = (Element) elements.get("businessContents");
		this.businessItem = (Element) elements.get("businessItem");
		this.Email = (Element) elements.get("Email");
		this.delButton = (Element) elements.get("delButton");
		this.town = (Element) elements.get("town");
		this.amountOfFounds = (Element) elements.get("amountOfFounds");
		this.companyShortName = (Element) elements.get("companyShortName");
		this.representativeName_lastName = (Element) elements.get("representativeName_lastName");
		this.companyKana = (Element) elements.get("companyKana");
		this.addressKana_disp = (Element) elements.get("addressKana_disp");
		this.representativeName_lastNameKana = (Element) elements.get("representativeName_lastNameKana");
		this.name = (Element) elements.get("name");
		this.representativeNameKaana_1 = (Element) elements.get("representativeNameKaana_1");
		this.streetNumber = (Element) elements.get("streetNumber");
		this.URL = (Element) elements.get("URL");
		this.representativeName_firstNameKana = (Element) elements.get("representativeName_firstNameKana");
		this.endingMonth = (Element) elements.get("endingMonth");
		this.streetNumberKana = (Element) elements.get("streetNumberKana");
		this.telephoneNum_1 = (Element) elements.get("telephoneNum_1");
		this.representativeName_1 = (Element) elements.get("representativeName_1");
		this.zipCode_search = (Element) elements.get("zipCode_search");
		this.telephoneNum_2 = (Element) elements.get("telephoneNum_2");
		this.representativeName_2 = (Element) elements.get("representativeName_2");
		this.telephoneNum_3 = (Element) elements.get("telephoneNum_3");
		this.edit = (Element) elements.get("edit");
		this.localGovernmentCode = (Element) elements.get("localGovernmentCode");
		this.zipCode_disp = (Element) elements.get("zipCode_disp");
		this.companyCode = (Element) elements.get("companyCode");
		this.prefecture = (Element) elements.get("prefecture");
		this.cellNum_2 = (Element) elements.get("cellNum_2");
		this.representativeName_disp = (Element) elements.get("representativeName_disp");
		this.cellNum_1 = (Element) elements.get("cellNum_1");
		this.okForRemove = (Element) elements.get("okForRemove");
		this.cellNum_3 = (Element) elements.get("cellNum_3");
	}
}
