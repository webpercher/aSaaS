/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class CompanyClientLayout implements Page {
    // Declare variable
	public Element closingType_Mouth;
	public Element companyTypePlaceBefore;
	public Element save;
	public Element accountPeriod_StartMonth;
	public Element taxMethodAndTransferType_Include;
	public Element close;
	public Element fractionOfSalesHandlingType;
	public Element companyKanaDisp;
	public Element companyDisplayName;
	public Element taxMethodAndTransferType_Exclude;
	public Element file;
	public Element businessSegmentUsageType_No;
	public Element ok;
	public Element taxationMethod_General;
	public Element closingDate_Era;
	public Element closingType_HalfYear;
	public Element businessPersonType_Applicable;
	public Element code;
	public Element companyName;
	public Element accountStructureDisp;
	public Element codeDisp;
	public Element accountStructure;
	public Element accountPeriod_EndYear;
	public Element accountPeriod_EndDay;
	public Element accountPeriod_StartDay;
	public Element createNewPeriod;
	public Element manufacturingCostTypeDisp;
	public Element businessSegmentUsageType_Yes;
	public Element companyNameDisp;
	public Element companyShortName;
	public Element taxationMethodDisp;
	public Element closingType_Quaterly;
	public Element accountPeriodDisp;
	public Element OK;
	public Element title;
	public Element fractionOfPurchaseHandlingTypeDisp;
	public Element fractionOfSalesHandlingTypeDisp;
	public Element companyTypePlaceAfter;
	public Element closingDate_Day;
	public Element name;
	public Element closingDate_Year;
	public Element taxationMethod_Simplified;
	public Element closingDateDisp;
	public Element businessPersonTypeDisp;
	public Element closingDate_Month;
	public Element closingTypeDisp;
	public Element taxMethodAdnTransferTypeDisp;
	public Element closingType_FullYear;
	public Element mainBusinessSegmentType;
	public Element companyOtherType;
	public Element manufacturingCostType_NotUse;
	public Element edit;
	public Element businessSegmentDisp;
	public Element businessPersonType_Exempt;
	public Element accountPeriod_EndMonth;
	public Element companyNameKana;
	public Element companyType;
	public Element manufacturingCostType_Use;
	public Element accountPeriod_StartYear;
	public Element businessSegmentUsageTypeDisp;
	public Element fractionOfPurchaseHandlingType;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.closingType_Mouth = (Element) elements.get("closingType_Mouth");
		this.companyTypePlaceBefore = (Element) elements.get("companyTypePlaceBefore");
		this.save = (Element) elements.get("save");
		this.accountPeriod_StartMonth = (Element) elements.get("accountPeriod_StartMonth");
		this.taxMethodAndTransferType_Include = (Element) elements.get("taxMethodAndTransferType_Include");
		this.close = (Element) elements.get("close");
		this.fractionOfSalesHandlingType = (Element) elements.get("fractionOfSalesHandlingType");
		this.companyKanaDisp = (Element) elements.get("companyKanaDisp");
		this.companyDisplayName = (Element) elements.get("companyDisplayName");
		this.taxMethodAndTransferType_Exclude = (Element) elements.get("taxMethodAndTransferType_Exclude");
		this.file = (Element) elements.get("file");
		this.businessSegmentUsageType_No = (Element) elements.get("businessSegmentUsageType_No");
		this.ok = (Element) elements.get("ok");
		this.taxationMethod_General = (Element) elements.get("taxationMethod_General");
		this.closingDate_Era = (Element) elements.get("closingDate_Era");
		this.closingType_HalfYear = (Element) elements.get("closingType_HalfYear");
		this.businessPersonType_Applicable = (Element) elements.get("businessPersonType_Applicable");
		this.code = (Element) elements.get("code");
		this.companyName = (Element) elements.get("companyName");
		this.accountStructureDisp = (Element) elements.get("accountStructureDisp");
		this.codeDisp = (Element) elements.get("codeDisp");
		this.accountStructure = (Element) elements.get("accountStructure");
		this.accountPeriod_EndYear = (Element) elements.get("accountPeriod_EndYear");
		this.accountPeriod_EndDay = (Element) elements.get("accountPeriod_EndDay");
		this.accountPeriod_StartDay = (Element) elements.get("accountPeriod_StartDay");
		this.createNewPeriod = (Element) elements.get("createNewPeriod");
		this.manufacturingCostTypeDisp = (Element) elements.get("manufacturingCostTypeDisp");
		this.businessSegmentUsageType_Yes = (Element) elements.get("businessSegmentUsageType_Yes");
		this.companyNameDisp = (Element) elements.get("companyNameDisp");
		this.companyShortName = (Element) elements.get("companyShortName");
		this.taxationMethodDisp = (Element) elements.get("taxationMethodDisp");
		this.closingType_Quaterly = (Element) elements.get("closingType_Quaterly");
		this.accountPeriodDisp = (Element) elements.get("accountPeriodDisp");
		this.OK = (Element) elements.get("OK");
		this.title = (Element) elements.get("title");
		this.fractionOfPurchaseHandlingTypeDisp = (Element) elements.get("fractionOfPurchaseHandlingTypeDisp");
		this.fractionOfSalesHandlingTypeDisp = (Element) elements.get("fractionOfSalesHandlingTypeDisp");
		this.companyTypePlaceAfter = (Element) elements.get("companyTypePlaceAfter");
		this.closingDate_Day = (Element) elements.get("closingDate_Day");
		this.name = (Element) elements.get("name");
		this.closingDate_Year = (Element) elements.get("closingDate_Year");
		this.taxationMethod_Simplified = (Element) elements.get("taxationMethod_Simplified");
		this.closingDateDisp = (Element) elements.get("closingDateDisp");
		this.businessPersonTypeDisp = (Element) elements.get("businessPersonTypeDisp");
		this.closingDate_Month = (Element) elements.get("closingDate_Month");
		this.closingTypeDisp = (Element) elements.get("closingTypeDisp");
		this.taxMethodAdnTransferTypeDisp = (Element) elements.get("taxMethodAdnTransferTypeDisp");
		this.closingType_FullYear = (Element) elements.get("closingType_FullYear");
		this.mainBusinessSegmentType = (Element) elements.get("mainBusinessSegmentType");
		this.companyOtherType = (Element) elements.get("companyOtherType");
		this.manufacturingCostType_NotUse = (Element) elements.get("manufacturingCostType_NotUse");
		this.edit = (Element) elements.get("edit");
		this.businessSegmentDisp = (Element) elements.get("businessSegmentDisp");
		this.businessPersonType_Exempt = (Element) elements.get("businessPersonType_Exempt");
		this.accountPeriod_EndMonth = (Element) elements.get("accountPeriod_EndMonth");
		this.companyNameKana = (Element) elements.get("companyNameKana");
		this.companyType = (Element) elements.get("companyType");
		this.manufacturingCostType_Use = (Element) elements.get("manufacturingCostType_Use");
		this.accountPeriod_StartYear = (Element) elements.get("accountPeriod_StartYear");
		this.businessSegmentUsageTypeDisp = (Element) elements.get("businessSegmentUsageTypeDisp");
		this.fractionOfPurchaseHandlingType = (Element) elements.get("fractionOfPurchaseHandlingType");
	}
}
