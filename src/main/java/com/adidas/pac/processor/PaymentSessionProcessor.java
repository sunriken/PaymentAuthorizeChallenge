package com.adidas.pac.processor;

import com.fasterxml.jackson.databind.JsonNode;

public class PaymentSessionProcessor {

  private static PaymentSessionProcessor instance;

  private PaymentSessionProcessor() {}

  public static PaymentSessionProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentSessionProcessor();
    }
    return instance;
  }

  public void process(JsonNode paymentSessionNode) {
    // TODO Auto-generated method stub

  }
}
