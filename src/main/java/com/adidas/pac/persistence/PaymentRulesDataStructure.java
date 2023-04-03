package com.adidas.pac.persistence;

import com.adidas.pac.model.input.PaymentRules;

public class PaymentRulesDataStructure extends InmutableDataStructure<PaymentRules> {
	private static PaymentRulesDataStructure instance;
	
	private PaymentRulesDataStructure() {
		super();
	}
	
	public static PaymentRulesDataStructure getInstance() {
		if(instance == null) {
			instance = new PaymentRulesDataStructure();
		}
		return instance;
	}
}
