/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class ChangeReportPage implements Page {
    // Declare variable
	public Element save;
	public Element businessStartDateInputDlag;
	public Element borrowingPresence;
	public Element presentationDate_ear;
	public Element approvalContractedUsefulLife;
	public Element responderPost_RDC;
	public Element popUp_standarAssessmentDisplay;
	public Element list_thirdline;
	public Element list_secondline;
	public Element close;
	public Element possessionDivisionBusinessHouse;
	public Element responderName_RDC;
	public Element popUp_decreaseReasonDisplay;
	public Element standarAssessmentException;
	public Element responderName;
	public Element responderTelephone_1;
	public Element responderTelephone_2;
	public Element responderTelephone_3;
	public Element menuBar_input_ChangeReportSetting;
	public Element secondLine;
	public Element popUp_decisionAmountDisplay;
	public Element ownerCode;
	public Element popUp_residualRateDisplay;
	public Element legalAccountingDprcMethod;
	public Element presentationDate_month;
	public Element menuBar_input_ChangeReportSetting_2;
	public Element reportDprcCommon_date;
	public Element structures;
	public Element presentationDate_year;
	public Element businessItem;
	public Element list_firstline;
	public Element businessStartDate_year_RDC;
	public Element thirdLine;
	public Element RO_presentationDate_dis;
	public Element presentationDateFlag;
	public Element RO_presentationDate_year;
	public Element businessStartDate_year;
	public Element machinery;
	public Element popUp_amountDisplay;
	public Element specialDprcReductionEntry;
	public Element reportReturnOffice;
	public Element popUp_confirm;
	public Element remarks;
	public Element popUp_taxationInstallation_2;
	public Element responderTelephone_3_RDC;
	public Element businessStartDate_ear_RDC;
	public Element popUp_standarAssessmentDisplay_2;
	public Element RO_presentationDate_ear;
	public Element responderPost;
	public Element menuBar_input;
	public Element reportReturnOfficeSetting;
	public Element popUp_SAED;
	public Element popUp_prevAcquisitionCost;
	public Element blueReport;
	public Element name;
	public Element responderTelephone_2_RDC;
	public Element firstLine;
	public Element responderInputFlag;
	public Element increaseDprc;
	public Element popUp_taxationInstallation;
	public Element presentationDate_day;
	public Element edit;
	public Element popUp_valuationDisplay;
	public Element capitalAsOther;
	public Element RO_presentationDate_month;
	public Element popUp_reportDivision;
	public Element businessStartDate_month_RDC;
	public Element popUp_increaseReasonDisplay;
	public Element businessStartDate_month;
	public Element landlordName;
	public Element responderTelephone_1_RDC;
	public Element popUp_decreaseSummaryDisplay;
	public Element popUp_summaryDisplay;
	public Element businessStartDate_ear;
	public Element RO_presentationDate_day;
	public Element popUp_codeDisplay;
	public Element popUp_SAFM;
	public Element popUp_decreaseCodeDisplay;
	public Element taxFreeAsset;
	public Element popUp_quantitySubTotal;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.save = (Element) elements.get("save");
		this.businessStartDateInputDlag = (Element) elements.get("businessStartDateInputDlag");
		this.borrowingPresence = (Element) elements.get("borrowingPresence");
		this.presentationDate_ear = (Element) elements.get("presentationDate_ear");
		this.approvalContractedUsefulLife = (Element) elements.get("approvalContractedUsefulLife");
		this.responderPost_RDC = (Element) elements.get("responderPost_RDC");
		this.popUp_standarAssessmentDisplay = (Element) elements.get("popUp_standarAssessmentDisplay");
		this.list_thirdline = (Element) elements.get("list_thirdline");
		this.list_secondline = (Element) elements.get("list_secondline");
		this.close = (Element) elements.get("close");
		this.possessionDivisionBusinessHouse = (Element) elements.get("possessionDivisionBusinessHouse");
		this.responderName_RDC = (Element) elements.get("responderName_RDC");
		this.popUp_decreaseReasonDisplay = (Element) elements.get("popUp_decreaseReasonDisplay");
		this.standarAssessmentException = (Element) elements.get("standarAssessmentException");
		this.responderName = (Element) elements.get("responderName");
		this.responderTelephone_1 = (Element) elements.get("responderTelephone_1");
		this.responderTelephone_2 = (Element) elements.get("responderTelephone_2");
		this.responderTelephone_3 = (Element) elements.get("responderTelephone_3");
		this.menuBar_input_ChangeReportSetting = (Element) elements.get("menuBar_input_ChangeReportSetting");
		this.secondLine = (Element) elements.get("secondLine");
		this.popUp_decisionAmountDisplay = (Element) elements.get("popUp_decisionAmountDisplay");
		this.ownerCode = (Element) elements.get("ownerCode");
		this.popUp_residualRateDisplay = (Element) elements.get("popUp_residualRateDisplay");
		this.legalAccountingDprcMethod = (Element) elements.get("legalAccountingDprcMethod");
		this.presentationDate_month = (Element) elements.get("presentationDate_month");
		this.menuBar_input_ChangeReportSetting_2 = (Element) elements.get("menuBar_input_ChangeReportSetting_2");
		this.reportDprcCommon_date = (Element) elements.get("reportDprcCommon_date");
		this.structures = (Element) elements.get("structures");
		this.presentationDate_year = (Element) elements.get("presentationDate_year");
		this.businessItem = (Element) elements.get("businessItem");
		this.list_firstline = (Element) elements.get("list_firstline");
		this.businessStartDate_year_RDC = (Element) elements.get("businessStartDate_year_RDC");
		this.thirdLine = (Element) elements.get("thirdLine");
		this.RO_presentationDate_dis = (Element) elements.get("RO_presentationDate_dis");
		this.presentationDateFlag = (Element) elements.get("presentationDateFlag");
		this.RO_presentationDate_year = (Element) elements.get("RO_presentationDate_year");
		this.businessStartDate_year = (Element) elements.get("businessStartDate_year");
		this.machinery = (Element) elements.get("machinery");
		this.popUp_amountDisplay = (Element) elements.get("popUp_amountDisplay");
		this.specialDprcReductionEntry = (Element) elements.get("specialDprcReductionEntry");
		this.reportReturnOffice = (Element) elements.get("reportReturnOffice");
		this.popUp_confirm = (Element) elements.get("popUp_confirm");
		this.remarks = (Element) elements.get("remarks");
		this.popUp_taxationInstallation_2 = (Element) elements.get("popUp_taxationInstallation_2");
		this.responderTelephone_3_RDC = (Element) elements.get("responderTelephone_3_RDC");
		this.businessStartDate_ear_RDC = (Element) elements.get("businessStartDate_ear_RDC");
		this.popUp_standarAssessmentDisplay_2 = (Element) elements.get("popUp_standarAssessmentDisplay_2");
		this.RO_presentationDate_ear = (Element) elements.get("RO_presentationDate_ear");
		this.responderPost = (Element) elements.get("responderPost");
		this.menuBar_input = (Element) elements.get("menuBar_input");
		this.reportReturnOfficeSetting = (Element) elements.get("reportReturnOfficeSetting");
		this.popUp_SAED = (Element) elements.get("popUp_SAED");
		this.popUp_prevAcquisitionCost = (Element) elements.get("popUp_prevAcquisitionCost");
		this.blueReport = (Element) elements.get("blueReport");
		this.name = (Element) elements.get("name");
		this.responderTelephone_2_RDC = (Element) elements.get("responderTelephone_2_RDC");
		this.firstLine = (Element) elements.get("firstLine");
		this.responderInputFlag = (Element) elements.get("responderInputFlag");
		this.increaseDprc = (Element) elements.get("increaseDprc");
		this.popUp_taxationInstallation = (Element) elements.get("popUp_taxationInstallation");
		this.presentationDate_day = (Element) elements.get("presentationDate_day");
		this.edit = (Element) elements.get("edit");
		this.popUp_valuationDisplay = (Element) elements.get("popUp_valuationDisplay");
		this.capitalAsOther = (Element) elements.get("capitalAsOther");
		this.RO_presentationDate_month = (Element) elements.get("RO_presentationDate_month");
		this.popUp_reportDivision = (Element) elements.get("popUp_reportDivision");
		this.businessStartDate_month_RDC = (Element) elements.get("businessStartDate_month_RDC");
		this.popUp_increaseReasonDisplay = (Element) elements.get("popUp_increaseReasonDisplay");
		this.businessStartDate_month = (Element) elements.get("businessStartDate_month");
		this.landlordName = (Element) elements.get("landlordName");
		this.responderTelephone_1_RDC = (Element) elements.get("responderTelephone_1_RDC");
		this.popUp_decreaseSummaryDisplay = (Element) elements.get("popUp_decreaseSummaryDisplay");
		this.popUp_summaryDisplay = (Element) elements.get("popUp_summaryDisplay");
		this.businessStartDate_ear = (Element) elements.get("businessStartDate_ear");
		this.RO_presentationDate_day = (Element) elements.get("RO_presentationDate_day");
		this.popUp_codeDisplay = (Element) elements.get("popUp_codeDisplay");
		this.popUp_SAFM = (Element) elements.get("popUp_SAFM");
		this.popUp_decreaseCodeDisplay = (Element) elements.get("popUp_decreaseCodeDisplay");
		this.taxFreeAsset = (Element) elements.get("taxFreeAsset");
		this.popUp_quantitySubTotal = (Element) elements.get("popUp_quantitySubTotal");
	}
}
