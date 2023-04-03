package com.adidas.pac.persistence;

import com.adidas.pac.model.input.PaymentSession;

public class PaymentSessionDataStructure extends InmutableDataStructure<PaymentSession> {
	private static PaymentSessionDataStructure instance;
	
	private PaymentSessionDataStructure() {
		super();
	}
	
	public static PaymentSessionDataStructure getInstance() {
		if(instance == null) {
			instance = new PaymentSessionDataStructure();
		}
		return instance;
	}
}