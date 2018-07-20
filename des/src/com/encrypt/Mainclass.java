package com.encrypt;
public class Mainclass {
	
	public static void main(String[] args) {
		
		DataElementUtill dataElementUtill = new DataElementUtill(122, "+91", "result", "EUR 1", "06/07/2017", "Payment", 11111111);
		String message = dataElementUtill.getAmount() + dataElementUtill.getCountryCode() +
				dataElementUtill.getVerificationResult() + dataElementUtill.getCountryCode() + dataElementUtill.getDate() + 
				dataElementUtill.getTransactionType() + dataElementUtill.getUnpredictableNumber();
			
		System.out.println("Original message " + message);
		System.out.println(message.length());
		TripleDes.cryptogram(message);
		}
}
