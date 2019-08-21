/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class IndivdualClientLayout implements Page {
    // Declare variable
	public Element save;
	public Element businessSegment_Yes;
	public Element tradeNameDisp;
	public Element fractionOfSaleTypeDisp;
	public Element accountPeriod_StartMonth;
	public Element tradeName;
	public Element taxMethodAndTransferType_Include;
	public Element close;
	public Element taxMethodAndTransferType_Exclude;
	public Element fractionOfPurchaseTypeDisp;
	public Element ok;
	public Element taxationMethod_General;
	public Element lastName;
	public Element closingDate_Era;
	public Element businessPersonType_ApplicablePerson;
	public Element nameDisp;
	public Element code;
	public Element accountStructureDisp;
	public Element codeDisp;
	public Element accountStructure;
	public Element accountPeriod_EndYear;
	public Element accountPeriod_EndDay;
	public Element accountPeriod_StartDay;
	public Element manufacturingCostTypeDisp;
	public Element mainBusinessSegType;
	public Element businessSegmentUsageDisp;
	public Element taxationMethodDisp;
	public Element accountPeriodDisp;
	public Element output_Name;
	public Element output_TradeName;
	public Element closingDate_Day;
	public Element name;
	public Element closingDate_Year;
	public Element taxationMethod_Simplified;
	public Element closingDateDisp;
	public Element outputDisp;
	public Element businessSegment_No;
	public Element closingDate_Month;
	public Element firstName;
	public Element fractionOfSaleType;
	public Element firstNameKana;
	public Element accountPeriod_StartEra;
	public Element taxMethodAndTransferTypeDisp;
	public Element manufacturingCostType_NotUse;
	public Element edit;
	public Element fractionOfPurchaseType;
	public Element accountPeriod_EndEra;
	public Element nameKanaDisp;
	public Element accountPeriod_EndMonth;
	public Element manufacturingCostType_Use;
	public Element businessPersonType_ExemptPerson;
	public Element accountPeriod_StartYear;
	public Element lastNameKana;
	public Element consumptionTax_BusinessTypeDisp;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.save = (Element) elements.get("save");
		this.businessSegment_Yes = (Element) elements.get("businessSegment_Yes");
		this.tradeNameDisp = (Element) elements.get("tradeNameDisp");
		this.fractionOfSaleTypeDisp = (Element) elements.get("fractionOfSaleTypeDisp");
		this.accountPeriod_StartMonth = (Element) elements.get("accountPeriod_StartMonth");
		this.tradeName = (Element) elements.get("tradeName");
		this.taxMethodAndTransferType_Include = (Element) elements.get("taxMethodAndTransferType_Include");
		this.close = (Element) elements.get("close");
		this.taxMethodAndTransferType_Exclude = (Element) elements.get("taxMethodAndTransferType_Exclude");
		this.fractionOfPurchaseTypeDisp = (Element) elements.get("fractionOfPurchaseTypeDisp");
		this.ok = (Element) elements.get("ok");
		this.taxationMethod_General = (Element) elements.get("taxationMethod_General");
		this.lastName = (Element) elements.get("lastName");
		this.closingDate_Era = (Element) elements.get("closingDate_Era");
		this.businessPersonType_ApplicablePerson = (Element) elements.get("businessPersonType_ApplicablePerson");
		this.nameDisp = (Element) elements.get("nameDisp");
		this.code = (Element) elements.get("code");
		this.accountStructureDisp = (Element) elements.get("accountStructureDisp");
		this.codeDisp = (Element) elements.get("codeDisp");
		this.accountStructure = (Element) elements.get("accountStructure");
		this.accountPeriod_EndYear = (Element) elements.get("accountPeriod_EndYear");
		this.accountPeriod_EndDay = (Element) elements.get("accountPeriod_EndDay");
		this.accountPeriod_StartDay = (Element) elements.get("accountPeriod_StartDay");
		this.manufacturingCostTypeDisp = (Element) elements.get("manufacturingCostTypeDisp");
		this.mainBusinessSegType = (Element) elements.get("mainBusinessSegType");
		this.businessSegmentUsageDisp = (Element) elements.get("businessSegmentUsageDisp");
		this.taxationMethodDisp = (Element) elements.get("taxationMethodDisp");
		this.accountPeriodDisp = (Element) elements.get("accountPeriodDisp");
		this.output_Name = (Element) elements.get("output_Name");
		this.output_TradeName = (Element) elements.get("output_TradeName");
		this.closingDate_Day = (Element) elements.get("closingDate_Day");
		this.name = (Element) elements.get("name");
		this.closingDate_Year = (Element) elements.get("closingDate_Year");
		this.taxationMethod_Simplified = (Element) elements.get("taxationMethod_Simplified");
		this.closingDateDisp = (Element) elements.get("closingDateDisp");
		this.outputDisp = (Element) elements.get("outputDisp");
		this.businessSegment_No = (Element) elements.get("businessSegment_No");
		this.closingDate_Month = (Element) elements.get("closingDate_Month");
		this.firstName = (Element) elements.get("firstName");
		this.fractionOfSaleType = (Element) elements.get("fractionOfSaleType");
		this.firstNameKana = (Element) elements.get("firstNameKana");
		this.accountPeriod_StartEra = (Element) elements.get("accountPeriod_StartEra");
		this.taxMethodAndTransferTypeDisp = (Element) elements.get("taxMethodAndTransferTypeDisp");
		this.manufacturingCostType_NotUse = (Element) elements.get("manufacturingCostType_NotUse");
		this.edit = (Element) elements.get("edit");
		this.fractionOfPurchaseType = (Element) elements.get("fractionOfPurchaseType");
		this.accountPeriod_EndEra = (Element) elements.get("accountPeriod_EndEra");
		this.nameKanaDisp = (Element) elements.get("nameKanaDisp");
		this.accountPeriod_EndMonth = (Element) elements.get("accountPeriod_EndMonth");
		this.manufacturingCostType_Use = (Element) elements.get("manufacturingCostType_Use");
		this.businessPersonType_ExemptPerson = (Element) elements.get("businessPersonType_ExemptPerson");
		this.accountPeriod_StartYear = (Element) elements.get("accountPeriod_StartYear");
		this.lastNameKana = (Element) elements.get("lastNameKana");
		this.consumptionTax_BusinessTypeDisp = (Element) elements.get("consumptionTax_BusinessTypeDisp");
	}
}
