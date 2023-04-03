package com.adidas.pac.processors;

import com.fasterxml.jackson.databind.JsonNode;

public class PaymentRulesProcessor {

  private static PaymentRulesProcessor instance;

  private PaymentRulesProcessor() {}

  public static PaymentRulesProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentRulesProcessor();
    }
    return instance;
  }

  public void process(JsonNode paymentRulesNode) {
    // TODO Auto-generated method stub

  }
}
