package com.encrypt;

public class DataElementUtill {
	
	private double amount;
	
	private String countryCode;
	
	private String verificationResult;
	
	private String currencyCode;
	
	private String date;
	
	private String transactionType;
	
	private int unpredictableNumber;
	
	public DataElementUtill() {}

	public DataElementUtill(double amount, String countryCode, String verificationResult,
			String currencyCode, String date, String transactionType, int unpredictableNumber) {
		this.amount = amount;
		this.countryCode = countryCode;
		this.verificationResult = verificationResult;
		this.currencyCode = currencyCode;
		this.date = date;
		this.transactionType = transactionType;
		this.unpredictableNumber = unpredictableNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getVerificationResult() {
		return verificationResult;
	}

	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getUnpredictableNumber() {
		return unpredictableNumber;
	}

	public void setUnpredictableNumber(int unpredictableNumber) {
		this.unpredictableNumber = unpredictableNumber;
	}
}
