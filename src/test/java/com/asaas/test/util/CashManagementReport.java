/**
 * 
 */
package com.asaas.test.util;

import java.util.HashMap;

/**
 * @author
 * 
 */
public class CashManagementReport implements Page {
    // Declare variable
	public Element totalTreasuryExpenditure;
	public Element otherordinaryExp;
	public Element nonoperatingIncome_t;
	public Element longTermLoansPayRet;
	public Element fixedAssetSale;
	public Element moneyUnit;
	public Element menu_AccountBalance;
	public Element selectedMonth;
	public Element close;
	public Element totalTreasuryIncome_t;
	public Element advanceReceived_t;
	public Element shortTermLoans;
	public Element nextCarrying2;
	public Element totalOrdinaryIncome;
	public Element longTermLoans;
	public Element otherTreasuryIncome;
	public Element fixedassetBuy;
	public Element shortTermLoansPayRet;
	public Element totalOrdinaryIncome_t;
	public Element nextCarrying1;
	public Element moneyInvPayOff;
	public Element totalOrdinaryIncome11;
	public Element totalOrdinaryIncome10;
	public Element firstMonth;
	public Element bottomMenu;
	public Element advanceReceived;
	public Element otherTreExp;
	public Element totalTreExp;
	public Element investmentEar;
	public Element excessAmount;
	public Element otherOrdinaryIncome;
	public Element totalOrdinaryIncome9;
	public Element totalOrdinaryIncome8;
	public Element totalOrdinaryIncome7;
	public Element discountingOfABill;
	public Element chargeCollection;
	public Element totalOrdinaryIncome2;
	public Element totalOrdinaryIncome1;
	public Element fixedDepDep;
	public Element totalOrdinaryIncome6;
	public Element totalOrdinaryIncome5;
	public Element totalOrdinaryIncome12;
	public Element totalOrdinaryIncome4;
	public Element totalOrdinaryIncome3;
	public Element nonoperatingIncome;
	public Element otherOrdinaryIncome_t;
	public Element interestIncome_t;
	public Element securitiesSale;
	public Element securitiesBuy;
	public Element lastQuarter;
	public Element cashSales;
	public Element chargeForCollection;
	public Element collectionBill;
	public Element laborCosts;
	public Element previousCarrying2;
	public Element previousCarrying1;
	public Element billPayable;
	public Element collectiongOfBills;
	public Element name;
	public Element totalordinaryExp;
	public Element del;
	public Element sundryExp;
	public Element arrearsAndDisExp;
	public Element cashSales12;
	public Element menuBar;
	public Element esc;
	public Element menu_Login;
	public Element settingExp;
	public Element settingIncome;
	public Element Title;
	public Element cashSales2;
	public Element accountsPayble;
	public Element interestIncome;
	public Element cashSales1;
	public Element fixedDepPayOff;
	public Element cashPurchase;
	public Element totalTreasuryIncome;
	public Element interestAndDisExp;
	public Element investmentSpending;

    /*
     * @see com.asaas.test.util.Page#init(java.util.HashMap)
     */
    public void init(HashMap<String, Object> elements) {
        // Initialize variable
		this.totalTreasuryExpenditure = (Element) elements.get("totalTreasuryExpenditure");
		this.otherordinaryExp = (Element) elements.get("otherordinaryExp");
		this.nonoperatingIncome_t = (Element) elements.get("nonoperatingIncome_t");
		this.longTermLoansPayRet = (Element) elements.get("longTermLoansPayRet");
		this.fixedAssetSale = (Element) elements.get("fixedAssetSale");
		this.moneyUnit = (Element) elements.get("moneyUnit");
		this.menu_AccountBalance = (Element) elements.get("menu_AccountBalance");
		this.selectedMonth = (Element) elements.get("selectedMonth");
		this.close = (Element) elements.get("close");
		this.totalTreasuryIncome_t = (Element) elements.get("totalTreasuryIncome_t");
		this.advanceReceived_t = (Element) elements.get("advanceReceived_t");
		this.shortTermLoans = (Element) elements.get("shortTermLoans");
		this.nextCarrying2 = (Element) elements.get("nextCarrying2");
		this.totalOrdinaryIncome = (Element) elements.get("totalOrdinaryIncome");
		this.longTermLoans = (Element) elements.get("longTermLoans");
		this.otherTreasuryIncome = (Element) elements.get("otherTreasuryIncome");
		this.fixedassetBuy = (Element) elements.get("fixedassetBuy");
		this.shortTermLoansPayRet = (Element) elements.get("shortTermLoansPayRet");
		this.totalOrdinaryIncome_t = (Element) elements.get("totalOrdinaryIncome_t");
		this.nextCarrying1 = (Element) elements.get("nextCarrying1");
		this.moneyInvPayOff = (Element) elements.get("moneyInvPayOff");
		this.totalOrdinaryIncome11 = (Element) elements.get("totalOrdinaryIncome11");
		this.totalOrdinaryIncome10 = (Element) elements.get("totalOrdinaryIncome10");
		this.firstMonth = (Element) elements.get("firstMonth");
		this.bottomMenu = (Element) elements.get("bottomMenu");
		this.advanceReceived = (Element) elements.get("advanceReceived");
		this.otherTreExp = (Element) elements.get("otherTreExp");
		this.totalTreExp = (Element) elements.get("totalTreExp");
		this.investmentEar = (Element) elements.get("investmentEar");
		this.excessAmount = (Element) elements.get("excessAmount");
		this.otherOrdinaryIncome = (Element) elements.get("otherOrdinaryIncome");
		this.totalOrdinaryIncome9 = (Element) elements.get("totalOrdinaryIncome9");
		this.totalOrdinaryIncome8 = (Element) elements.get("totalOrdinaryIncome8");
		this.totalOrdinaryIncome7 = (Element) elements.get("totalOrdinaryIncome7");
		this.discountingOfABill = (Element) elements.get("discountingOfABill");
		this.chargeCollection = (Element) elements.get("chargeCollection");
		this.totalOrdinaryIncome2 = (Element) elements.get("totalOrdinaryIncome2");
		this.totalOrdinaryIncome1 = (Element) elements.get("totalOrdinaryIncome1");
		this.fixedDepDep = (Element) elements.get("fixedDepDep");
		this.totalOrdinaryIncome6 = (Element) elements.get("totalOrdinaryIncome6");
		this.totalOrdinaryIncome5 = (Element) elements.get("totalOrdinaryIncome5");
		this.totalOrdinaryIncome12 = (Element) elements.get("totalOrdinaryIncome12");
		this.totalOrdinaryIncome4 = (Element) elements.get("totalOrdinaryIncome4");
		this.totalOrdinaryIncome3 = (Element) elements.get("totalOrdinaryIncome3");
		this.nonoperatingIncome = (Element) elements.get("nonoperatingIncome");
		this.otherOrdinaryIncome_t = (Element) elements.get("otherOrdinaryIncome_t");
		this.interestIncome_t = (Element) elements.get("interestIncome_t");
		this.securitiesSale = (Element) elements.get("securitiesSale");
		this.securitiesBuy = (Element) elements.get("securitiesBuy");
		this.lastQuarter = (Element) elements.get("lastQuarter");
		this.cashSales = (Element) elements.get("cashSales");
		this.chargeForCollection = (Element) elements.get("chargeForCollection");
		this.collectionBill = (Element) elements.get("collectionBill");
		this.laborCosts = (Element) elements.get("laborCosts");
		this.previousCarrying2 = (Element) elements.get("previousCarrying2");
		this.previousCarrying1 = (Element) elements.get("previousCarrying1");
		this.billPayable = (Element) elements.get("billPayable");
		this.collectiongOfBills = (Element) elements.get("collectiongOfBills");
		this.name = (Element) elements.get("name");
		this.totalordinaryExp = (Element) elements.get("totalordinaryExp");
		this.del = (Element) elements.get("del");
		this.sundryExp = (Element) elements.get("sundryExp");
		this.arrearsAndDisExp = (Element) elements.get("arrearsAndDisExp");
		this.cashSales12 = (Element) elements.get("cashSales12");
		this.menuBar = (Element) elements.get("menuBar");
		this.esc = (Element) elements.get("esc");
		this.menu_Login = (Element) elements.get("menu_Login");
		this.settingExp = (Element) elements.get("settingExp");
		this.settingIncome = (Element) elements.get("settingIncome");
		this.Title = (Element) elements.get("Title");
		this.cashSales2 = (Element) elements.get("cashSales2");
		this.accountsPayble = (Element) elements.get("accountsPayble");
		this.interestIncome = (Element) elements.get("interestIncome");
		this.cashSales1 = (Element) elements.get("cashSales1");
		this.fixedDepPayOff = (Element) elements.get("fixedDepPayOff");
		this.cashPurchase = (Element) elements.get("cashPurchase");
		this.totalTreasuryIncome = (Element) elements.get("totalTreasuryIncome");
		this.interestAndDisExp = (Element) elements.get("interestAndDisExp");
		this.investmentSpending = (Element) elements.get("investmentSpending");
	}
}
