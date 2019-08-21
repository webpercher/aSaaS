/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ChangeTaxReportSettingPage implements Page {
    // Declare variable
	public Element street;
	public Element cancel;
	public Element establishment_day;
	public Element procuratorBuilding;
	public Element city;
	public Element establishment_month;
	public Element representerZip_2;
	public Element representerZip_1;
	public Element procuratorPrefecture;
	public Element managerID;
	public Element establishment_ear;
	public Element establishment_year;
	public Element representerPrefecture;
	public Element town_kana;
	public Element building_kana;
	public Element procuratorTown;
	public Element businessDescription;
	public Element procuratorNameKana_2;
	public Element city_kana;
	public Element representerStreet;
	public Element procuratorStreet;
	public Element amountOfFunds;
	public Element procuratorTelephone_1;
	public Element procuratorNameKana_1;
	public Element procuratorTelephone_3;
	public Element procuratorTelephone_2;
	public Element procuratorName_1;
	public Element procuratorName_2;
	public Element street_kana;
	public Element town;
	public Element representerNmae_2;
	public Element representerNmae_1;
	public Element closing_day;
	public Element managerNum_1;
	public Element prefecture_kana;
	public Element managerNum_4;
	public Element representerBuilding;
	public Element managerNum_2;
	public Element managerNum_3;
	public Element telephone_1;
	public Element telephone_2;
	public Element representerTelephone_1;
	public Element OK;
	public Element representerTelephone_2;
	public Element representerTelephone_3;
	public Element name;
	public Element personNum_3;
	public Element personNum_2;
	public Element representerTown;
	public Element personNum_4;
	public Element personNum_1;
	public Element telephone_3;
	public Element building;
	public Element procuratorCity;
	public Element managerName_1;
	public Element managerName_2;
	public Element procuratorZip_2;
	public Element procuratorZip_1;
	public Element zipCode_1;
	public Element zipCode_2;
	public Element closing_month;
	public Element register;
	public Element representerCity;
	public Element prefecture;
	public Element procuratorSearch;
	public Element representerNmaeKana_1;
	public Element representerNmaeKana_2;
	public Element personID;
	public Element name_kana;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.street = (Element) elements.get("street");
		this.cancel = (Element) elements.get("cancel");
		this.establishment_day = (Element) elements.get("establishment_day");
		this.procuratorBuilding = (Element) elements.get("procuratorBuilding");
		this.city = (Element) elements.get("city");
		this.establishment_month = (Element) elements.get("establishment_month");
		this.representerZip_2 = (Element) elements.get("representerZip_2");
		this.representerZip_1 = (Element) elements.get("representerZip_1");
		this.procuratorPrefecture = (Element) elements.get("procuratorPrefecture");
		this.managerID = (Element) elements.get("managerID");
		this.establishment_ear = (Element) elements.get("establishment_ear");
		this.establishment_year = (Element) elements.get("establishment_year");
		this.representerPrefecture = (Element) elements.get("representerPrefecture");
		this.town_kana = (Element) elements.get("town_kana");
		this.building_kana = (Element) elements.get("building_kana");
		this.procuratorTown = (Element) elements.get("procuratorTown");
		this.businessDescription = (Element) elements.get("businessDescription");
		this.procuratorNameKana_2 = (Element) elements.get("procuratorNameKana_2");
		this.city_kana = (Element) elements.get("city_kana");
		this.representerStreet = (Element) elements.get("representerStreet");
		this.procuratorStreet = (Element) elements.get("procuratorStreet");
		this.amountOfFunds = (Element) elements.get("amountOfFunds");
		this.procuratorTelephone_1 = (Element) elements.get("procuratorTelephone_1");
		this.procuratorNameKana_1 = (Element) elements.get("procuratorNameKana_1");
		this.procuratorTelephone_3 = (Element) elements.get("procuratorTelephone_3");
		this.procuratorTelephone_2 = (Element) elements.get("procuratorTelephone_2");
		this.procuratorName_1 = (Element) elements.get("procuratorName_1");
		this.procuratorName_2 = (Element) elements.get("procuratorName_2");
		this.street_kana = (Element) elements.get("street_kana");
		this.town = (Element) elements.get("town");
		this.representerNmae_2 = (Element) elements.get("representerNmae_2");
		this.representerNmae_1 = (Element) elements.get("representerNmae_1");
		this.closing_day = (Element) elements.get("closing_day");
		this.managerNum_1 = (Element) elements.get("managerNum_1");
		this.prefecture_kana = (Element) elements.get("prefecture_kana");
		this.managerNum_4 = (Element) elements.get("managerNum_4");
		this.representerBuilding = (Element) elements.get("representerBuilding");
		this.managerNum_2 = (Element) elements.get("managerNum_2");
		this.managerNum_3 = (Element) elements.get("managerNum_3");
		this.telephone_1 = (Element) elements.get("telephone_1");
		this.telephone_2 = (Element) elements.get("telephone_2");
		this.representerTelephone_1 = (Element) elements.get("representerTelephone_1");
		this.OK = (Element) elements.get("OK");
		this.representerTelephone_2 = (Element) elements.get("representerTelephone_2");
		this.representerTelephone_3 = (Element) elements.get("representerTelephone_3");
		this.name = (Element) elements.get("name");
		this.personNum_3 = (Element) elements.get("personNum_3");
		this.personNum_2 = (Element) elements.get("personNum_2");
		this.representerTown = (Element) elements.get("representerTown");
		this.personNum_4 = (Element) elements.get("personNum_4");
		this.personNum_1 = (Element) elements.get("personNum_1");
		this.telephone_3 = (Element) elements.get("telephone_3");
		this.building = (Element) elements.get("building");
		this.procuratorCity = (Element) elements.get("procuratorCity");
		this.managerName_1 = (Element) elements.get("managerName_1");
		this.managerName_2 = (Element) elements.get("managerName_2");
		this.procuratorZip_2 = (Element) elements.get("procuratorZip_2");
		this.procuratorZip_1 = (Element) elements.get("procuratorZip_1");
		this.zipCode_1 = (Element) elements.get("zipCode_1");
		this.zipCode_2 = (Element) elements.get("zipCode_2");
		this.closing_month = (Element) elements.get("closing_month");
		this.register = (Element) elements.get("register");
		this.representerCity = (Element) elements.get("representerCity");
		this.prefecture = (Element) elements.get("prefecture");
		this.procuratorSearch = (Element) elements.get("procuratorSearch");
		this.representerNmaeKana_1 = (Element) elements.get("representerNmaeKana_1");
		this.representerNmaeKana_2 = (Element) elements.get("representerNmaeKana_2");
		this.personID = (Element) elements.get("personID");
		this.name_kana = (Element) elements.get("name_kana");
	}
}
