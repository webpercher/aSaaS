/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class EmployeeSettingPage implements Page {
    // Declare variable
	public Element importExecuteButton;
	public Element inputMenu_bonusPaySlip;
	public Element printPageTitle2;
	public Element paymentCycleM;
	public Element fixedAmountFoundAmount_input;
	public Element printMenu_Slip_PaySlip;
	public Element employeeCode;
	public Element dependentsUnClaim;
	public Element employeeExistWornForm;
	public Element caculateUnitSalary;
	public Element joiningMonth;
	public Element YES;
	public Element fileMenu_ESC;
	public Element commuterAllowanceAmount_disp;
	public Element title_disp;
	public Element monthly_disp;
	public Element pensionFundsAmount;
	public Element printMenu_List_PaySlipList;
	public Element year;
	public Element printMenu_Payment_BonusDepositRequst;
	public Element bussnissType_construction;
	public Element employee2_InList;
	public Element joiningDay;
	public Element bonusBankTransfer;
	public Element employeeInformation;
	public Element pensionFundsAmount_input;
	public Element employeeSettingPage;
	public Element printMenu_Form_PayrollLegal;
	public Element bankName;
	public Element salaryBankTransfer;
	public Element monthly;
	public Element commuterType;
	public Element importErrorForm;
	public Element employeePage;
	public Element koMonthlyTable;
	public Element overFourtyHoursWork;
	public Element insured;
	public Element fixedAmountHealthInsurance_input;
	public Element registerMenu_Employee;
	public Element employeeClassification_disp;
	public Element pageTitle;
	public Element insuredHealthInsurance;
	public Element branchName;
	public Element month;
	public Element commuterAllowanceAmount;
	public Element overSixtyHoursWork;
	public Element paymentCycleDay;
	public Element fixedAmountFoundAmount;
	public Element employeeForms_SubmissionForms;
	public Element stdMonthlyRemuneration_name;
	public Element printMenu_Payment;
	public Element familyName;
	public Element statutoryHolidayLateNight;
	public Element NO;
	public Element dependentsNumber_disp;
	public Element title;
	public Element insured_name;
	public Element printMenu_Form;
	public Element printMenu_List_AnnualAdjustmentList;
	public Element employeeTwo;
	public Element makeAnnualAdjustment;
	public Element delete;
	public Element joiningYear;
	public Element paymentUnit_None;
	public Element fileMenu_Close;
	public Element employeeOne;
	public Element tenureStatus;
	public Element firstNameKana;
	public Element generalRewardMonthlyAmount_dis;
	public Element registerMenu;
	public Element menuBar;
	public Element importOKButton;
	public Element joiningEra;
	public Element selectButton;
	public Element registerMenu_Company;
	public Element printPageTitle;
	public Element employeeThree;
	public Element gender_male;
	public Element absenceDays_disp;
	public Element inputMenu_annualAdjustmentData;
	public Element absenceDays;
	public Element caculateUnitSalary_name;
	public Element generalRewardMonthlyAmount;
	public Element monthlyTableType;
	public Element addNew;
	public Element employeeFormsButton;
	public Element printMenu_Payment_SalaryDepositRequest;
	public Element retirementYear;
	public Element printMenu_Slip;
	public Element dependentsClaim;
	public Element save;
	public Element employeeBankInformation;
	public Element holidayNormal;
	public Element enteringDate;
	public Element employee1_InList;
	public Element cancel;
	public Element employeeImportButton;
	public Element employeeName_disp;
	public Element close;
	public Element confirmButton;
	public Element saveWornFormTwo;
	public Element paymentCycleHour;
	public Element fixedAmountHealthInsurance;
	public Element importReferButton;
	public Element popupForm_MSG;
	public Element retirementMonth;
	public Element stdMonthlyRemuneration;
	public Element employeeNameKana_disp;
	public Element dependentsNumber;
	public Element age_disp;
	public Element fixedAmountSocialSecurity;
	public Element bottomMenu;
	public Element insuranceCard;
	public Element fixedAmountSocialSecurity_input;
	public Element employee3_InList;
	public Element manualTitle;
	public Element socialSecurityNumber;
	public Element overtimeWorkHourlyPay_button;
	public Element printMenu_Document;
	public Element noWork_disp;
	public Element healthInsuranceGrade;
	public Element employeesPension;
	public Element weekdayLateNight;
	public Element employmentInsurance;
	public Element weekdayNormal;
	public Element statutoryHolidayNormal;
	public Element printMenu_Form_IncomeTax;
	public Element retirementEra;
	public Element employeeWornForm;
	public Element saveWornForm;
	public Element between40_65;
	public Element fixedAmountNursingInsurance_input;
	public Element bussnissType_other;
	public Element day;
	public Element familyNameKana;
	public Element printMenu_Payment_BonusTypeTable;
	public Element birthday_disp;
	public Element inputMenu;
	public Element stdMonthlyRemuneration_select;
	public Element salaryCash;
	public Element popUp;
	public Element calculateIncomeTaxAutomatically;
	public Element holidayLateNight;
	public Element era;
	public Element fixedAmountNursingInsurance;
	public Element generalBusiness;
	public Element employeeCode_disp;
	public Element totalTableLegalRecordSalaryIncome;
	public Element printMenu_Form_SummaryTable;
	public Element printMenu_List;
	public Element paymentUnit_Day;
	public Element retirementDay;
	public Element fixedAmountHealthInsurance_name;
	public Element name;
	public Element inputMenu_socialInsurance;
	public Element paymentCycleUM_pay1;
	public Element retirementDeath;
	public Element firstName;
	public Element paymentCycleUM_pay2;
	public Element noWork;
	public Element paymentCycleUM_pay3;
	public Element overtimeCost_disp;
	public Element printPage_Cancel;
	public Element commutingAllowancePaymentType3;
	public Element bonusCashPayment;
	public Element commutingAllowancePaymentType2;
	public Element commutingAllowancePaymentType1;
	public Element importErrorConfirm;
	public Element salaryCashPayment;
	public Element edit;
	public Element printMenu_Document_LegerOfW;
	public Element printMenu;
	public Element gender_fmale;
	public Element fileMenu__CreatPayrollPeriod;
	public Element workersAccidentInsuranceType1;
	public Element printMenu_Payment_PaymentMoneyType;
	public Element workersAccidentInsuranceType2;
	public Element workersAccidentInsuranceType3;
	public Element employeeFileButton;
	public Element printMenu_List_BonusSlipList;
	public Element insuredPerson;
	public Element bonusCash;
	public Element printMenu_Form_PayrollPayment;
	public Element selEmployeeBri;
	public Element printMenu_Slip_BonusSlip;
	public Element paymentUnit_Month;
	public Element inputMenu_salaryPaySlip;
	public Element overtimeCost;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.importExecuteButton = (Element) elements.get("importExecuteButton");
		this.inputMenu_bonusPaySlip = (Element) elements.get("inputMenu_bonusPaySlip");
		this.printPageTitle2 = (Element) elements.get("printPageTitle2");
		this.paymentCycleM = (Element) elements.get("paymentCycleM");
		this.fixedAmountFoundAmount_input = (Element) elements.get("fixedAmountFoundAmount_input");
		this.printMenu_Slip_PaySlip = (Element) elements.get("printMenu_Slip_PaySlip");
		this.employeeCode = (Element) elements.get("employeeCode");
		this.dependentsUnClaim = (Element) elements.get("dependentsUnClaim");
		this.employeeExistWornForm = (Element) elements.get("employeeExistWornForm");
		this.caculateUnitSalary = (Element) elements.get("caculateUnitSalary");
		this.joiningMonth = (Element) elements.get("joiningMonth");
		this.YES = (Element) elements.get("YES");
		this.fileMenu_ESC = (Element) elements.get("fileMenu_ESC");
		this.commuterAllowanceAmount_disp = (Element) elements.get("commuterAllowanceAmount_disp");
		this.title_disp = (Element) elements.get("title_disp");
		this.monthly_disp = (Element) elements.get("monthly_disp");
		this.pensionFundsAmount = (Element) elements.get("pensionFundsAmount");
		this.printMenu_List_PaySlipList = (Element) elements.get("printMenu_List_PaySlipList");
		this.year = (Element) elements.get("year");
		this.printMenu_Payment_BonusDepositRequst = (Element) elements.get("printMenu_Payment_BonusDepositRequst");
		this.bussnissType_construction = (Element) elements.get("bussnissType_construction");
		this.employee2_InList = (Element) elements.get("employee2_InList");
		this.joiningDay = (Element) elements.get("joiningDay");
		this.bonusBankTransfer = (Element) elements.get("bonusBankTransfer");
		this.employeeInformation = (Element) elements.get("employeeInformation");
		this.pensionFundsAmount_input = (Element) elements.get("pensionFundsAmount_input");
		this.employeeSettingPage = (Element) elements.get("employeeSettingPage");
		this.printMenu_Form_PayrollLegal = (Element) elements.get("printMenu_Form_PayrollLegal");
		this.bankName = (Element) elements.get("bankName");
		this.salaryBankTransfer = (Element) elements.get("salaryBankTransfer");
		this.monthly = (Element) elements.get("monthly");
		this.commuterType = (Element) elements.get("commuterType");
		this.importErrorForm = (Element) elements.get("importErrorForm");
		this.employeePage = (Element) elements.get("employeePage");
		this.koMonthlyTable = (Element) elements.get("koMonthlyTable");
		this.overFourtyHoursWork = (Element) elements.get("overFourtyHoursWork");
		this.insured = (Element) elements.get("insured");
		this.fixedAmountHealthInsurance_input = (Element) elements.get("fixedAmountHealthInsurance_input");
		this.registerMenu_Employee = (Element) elements.get("registerMenu_Employee");
		this.employeeClassification_disp = (Element) elements.get("employeeClassification_disp");
		this.pageTitle = (Element) elements.get("pageTitle");
		this.insuredHealthInsurance = (Element) elements.get("insuredHealthInsurance");
		this.branchName = (Element) elements.get("branchName");
		this.month = (Element) elements.get("month");
		this.commuterAllowanceAmount = (Element) elements.get("commuterAllowanceAmount");
		this.overSixtyHoursWork = (Element) elements.get("overSixtyHoursWork");
		this.paymentCycleDay = (Element) elements.get("paymentCycleDay");
		this.fixedAmountFoundAmount = (Element) elements.get("fixedAmountFoundAmount");
		this.employeeForms_SubmissionForms = (Element) elements.get("employeeForms_SubmissionForms");
		this.stdMonthlyRemuneration_name = (Element) elements.get("stdMonthlyRemuneration_name");
		this.printMenu_Payment = (Element) elements.get("printMenu_Payment");
		this.familyName = (Element) elements.get("familyName");
		this.statutoryHolidayLateNight = (Element) elements.get("statutoryHolidayLateNight");
		this.NO = (Element) elements.get("NO");
		this.dependentsNumber_disp = (Element) elements.get("dependentsNumber_disp");
		this.title = (Element) elements.get("title");
		this.insured_name = (Element) elements.get("insured_name");
		this.printMenu_Form = (Element) elements.get("printMenu_Form");
		this.printMenu_List_AnnualAdjustmentList = (Element) elements.get("printMenu_List_AnnualAdjustmentList");
		this.employeeTwo = (Element) elements.get("employeeTwo");
		this.makeAnnualAdjustment = (Element) elements.get("makeAnnualAdjustment");
		this.delete = (Element) elements.get("delete");
		this.joiningYear = (Element) elements.get("joiningYear");
		this.paymentUnit_None = (Element) elements.get("paymentUnit_None");
		this.fileMenu_Close = (Element) elements.get("fileMenu_Close");
		this.employeeOne = (Element) elements.get("employeeOne");
		this.tenureStatus = (Element) elements.get("tenureStatus");
		this.firstNameKana = (Element) elements.get("firstNameKana");
		this.generalRewardMonthlyAmount_dis = (Element) elements.get("generalRewardMonthlyAmount_dis");
		this.registerMenu = (Element) elements.get("registerMenu");
		this.menuBar = (Element) elements.get("menuBar");
		this.importOKButton = (Element) elements.get("importOKButton");
		this.joiningEra = (Element) elements.get("joiningEra");
		this.selectButton = (Element) elements.get("selectButton");
		this.registerMenu_Company = (Element) elements.get("registerMenu_Company");
		this.printPageTitle = (Element) elements.get("printPageTitle");
		this.employeeThree = (Element) elements.get("employeeThree");
		this.gender_male = (Element) elements.get("gender_male");
		this.absenceDays_disp = (Element) elements.get("absenceDays_disp");
		this.inputMenu_annualAdjustmentData = (Element) elements.get("inputMenu_annualAdjustmentData");
		this.absenceDays = (Element) elements.get("absenceDays");
		this.caculateUnitSalary_name = (Element) elements.get("caculateUnitSalary_name");
		this.generalRewardMonthlyAmount = (Element) elements.get("generalRewardMonthlyAmount");
		this.monthlyTableType = (Element) elements.get("monthlyTableType");
		this.addNew = (Element) elements.get("addNew");
		this.employeeFormsButton = (Element) elements.get("employeeFormsButton");
		this.printMenu_Payment_SalaryDepositRequest = (Element) elements.get("printMenu_Payment_SalaryDepositRequest");
		this.retirementYear = (Element) elements.get("retirementYear");
		this.printMenu_Slip = (Element) elements.get("printMenu_Slip");
		this.dependentsClaim = (Element) elements.get("dependentsClaim");
		this.save = (Element) elements.get("save");
		this.employeeBankInformation = (Element) elements.get("employeeBankInformation");
		this.holidayNormal = (Element) elements.get("holidayNormal");
		this.enteringDate = (Element) elements.get("enteringDate");
		this.employee1_InList = (Element) elements.get("employee1_InList");
		this.cancel = (Element) elements.get("cancel");
		this.employeeImportButton = (Element) elements.get("employeeImportButton");
		this.employeeName_disp = (Element) elements.get("employeeName_disp");
		this.close = (Element) elements.get("close");
		this.confirmButton = (Element) elements.get("confirmButton");
		this.saveWornFormTwo = (Element) elements.get("saveWornFormTwo");
		this.paymentCycleHour = (Element) elements.get("paymentCycleHour");
		this.fixedAmountHealthInsurance = (Element) elements.get("fixedAmountHealthInsurance");
		this.importReferButton = (Element) elements.get("importReferButton");
		this.popupForm_MSG = (Element) elements.get("popupForm_MSG");
		this.retirementMonth = (Element) elements.get("retirementMonth");
		this.stdMonthlyRemuneration = (Element) elements.get("stdMonthlyRemuneration");
		this.employeeNameKana_disp = (Element) elements.get("employeeNameKana_disp");
		this.dependentsNumber = (Element) elements.get("dependentsNumber");
		this.age_disp = (Element) elements.get("age_disp");
		this.fixedAmountSocialSecurity = (Element) elements.get("fixedAmountSocialSecurity");
		this.bottomMenu = (Element) elements.get("bottomMenu");
		this.insuranceCard = (Element) elements.get("insuranceCard");
		this.fixedAmountSocialSecurity_input = (Element) elements.get("fixedAmountSocialSecurity_input");
		this.employee3_InList = (Element) elements.get("employee3_InList");
		this.manualTitle = (Element) elements.get("manualTitle");
		this.socialSecurityNumber = (Element) elements.get("socialSecurityNumber");
		this.overtimeWorkHourlyPay_button = (Element) elements.get("overtimeWorkHourlyPay_button");
		this.printMenu_Document = (Element) elements.get("printMenu_Document");
		this.noWork_disp = (Element) elements.get("noWork_disp");
		this.healthInsuranceGrade = (Element) elements.get("healthInsuranceGrade");
		this.employeesPension = (Element) elements.get("employeesPension");
		this.weekdayLateNight = (Element) elements.get("weekdayLateNight");
		this.employmentInsurance = (Element) elements.get("employmentInsurance");
		this.weekdayNormal = (Element) elements.get("weekdayNormal");
		this.statutoryHolidayNormal = (Element) elements.get("statutoryHolidayNormal");
		this.printMenu_Form_IncomeTax = (Element) elements.get("printMenu_Form_IncomeTax");
		this.retirementEra = (Element) elements.get("retirementEra");
		this.employeeWornForm = (Element) elements.get("employeeWornForm");
		this.saveWornForm = (Element) elements.get("saveWornForm");
		this.between40_65 = (Element) elements.get("between40_65");
		this.fixedAmountNursingInsurance_input = (Element) elements.get("fixedAmountNursingInsurance_input");
		this.bussnissType_other = (Element) elements.get("bussnissType_other");
		this.day = (Element) elements.get("day");
		this.familyNameKana = (Element) elements.get("familyNameKana");
		this.printMenu_Payment_BonusTypeTable = (Element) elements.get("printMenu_Payment_BonusTypeTable");
		this.birthday_disp = (Element) elements.get("birthday_disp");
		this.inputMenu = (Element) elements.get("inputMenu");
		this.stdMonthlyRemuneration_select = (Element) elements.get("stdMonthlyRemuneration_select");
		this.salaryCash = (Element) elements.get("salaryCash");
		this.popUp = (Element) elements.get("popUp");
		this.calculateIncomeTaxAutomatically = (Element) elements.get("calculateIncomeTaxAutomatically");
		this.holidayLateNight = (Element) elements.get("holidayLateNight");
		this.era = (Element) elements.get("era");
		this.fixedAmountNursingInsurance = (Element) elements.get("fixedAmountNursingInsurance");
		this.generalBusiness = (Element) elements.get("generalBusiness");
		this.employeeCode_disp = (Element) elements.get("employeeCode_disp");
		this.totalTableLegalRecordSalaryIncome = (Element) elements.get("totalTableLegalRecordSalaryIncome");
		this.printMenu_Form_SummaryTable = (Element) elements.get("printMenu_Form_SummaryTable");
		this.printMenu_List = (Element) elements.get("printMenu_List");
		this.paymentUnit_Day = (Element) elements.get("paymentUnit_Day");
		this.retirementDay = (Element) elements.get("retirementDay");
		this.fixedAmountHealthInsurance_name = (Element) elements.get("fixedAmountHealthInsurance_name");
		this.name = (Element) elements.get("name");
		this.inputMenu_socialInsurance = (Element) elements.get("inputMenu_socialInsurance");
		this.paymentCycleUM_pay1 = (Element) elements.get("paymentCycleUM_pay1");
		this.retirementDeath = (Element) elements.get("retirementDeath");
		this.firstName = (Element) elements.get("firstName");
		this.paymentCycleUM_pay2 = (Element) elements.get("paymentCycleUM_pay2");
		this.noWork = (Element) elements.get("noWork");
		this.paymentCycleUM_pay3 = (Element) elements.get("paymentCycleUM_pay3");
		this.overtimeCost_disp = (Element) elements.get("overtimeCost_disp");
		this.printPage_Cancel = (Element) elements.get("printPage_Cancel");
		this.commutingAllowancePaymentType3 = (Element) elements.get("commutingAllowancePaymentType3");
		this.bonusCashPayment = (Element) elements.get("bonusCashPayment");
		this.commutingAllowancePaymentType2 = (Element) elements.get("commutingAllowancePaymentType2");
		this.commutingAllowancePaymentType1 = (Element) elements.get("commutingAllowancePaymentType1");
		this.importErrorConfirm = (Element) elements.get("importErrorConfirm");
		this.salaryCashPayment = (Element) elements.get("salaryCashPayment");
		this.edit = (Element) elements.get("edit");
		this.printMenu_Document_LegerOfW = (Element) elements.get("printMenu_Document_LegerOfW");
		this.printMenu = (Element) elements.get("printMenu");
		this.gender_fmale = (Element) elements.get("gender_fmale");
		this.fileMenu__CreatPayrollPeriod = (Element) elements.get("fileMenu__CreatPayrollPeriod");
		this.workersAccidentInsuranceType1 = (Element) elements.get("workersAccidentInsuranceType1");
		this.printMenu_Payment_PaymentMoneyType = (Element) elements.get("printMenu_Payment_PaymentMoneyType");
		this.workersAccidentInsuranceType2 = (Element) elements.get("workersAccidentInsuranceType2");
		this.workersAccidentInsuranceType3 = (Element) elements.get("workersAccidentInsuranceType3");
		this.employeeFileButton = (Element) elements.get("employeeFileButton");
		this.printMenu_List_BonusSlipList = (Element) elements.get("printMenu_List_BonusSlipList");
		this.insuredPerson = (Element) elements.get("insuredPerson");
		this.bonusCash = (Element) elements.get("bonusCash");
		this.printMenu_Form_PayrollPayment = (Element) elements.get("printMenu_Form_PayrollPayment");
		this.selEmployeeBri = (Element) elements.get("selEmployeeBri");
		this.printMenu_Slip_BonusSlip = (Element) elements.get("printMenu_Slip_BonusSlip");
		this.paymentUnit_Month = (Element) elements.get("paymentUnit_Month");
		this.inputMenu_salaryPaySlip = (Element) elements.get("inputMenu_salaryPaySlip");
		this.overtimeCost = (Element) elements.get("overtimeCost");
	}
}