package com.adidas.pac.processors;

public class PaymentSessionProcessor {

  private static PaymentSessionProcessor instance;

  private PaymentSessionProcessor() {
	  
  }  
  
  public static PaymentSessionProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentSessionProcessor();
    }
    return instance;
  }
}
