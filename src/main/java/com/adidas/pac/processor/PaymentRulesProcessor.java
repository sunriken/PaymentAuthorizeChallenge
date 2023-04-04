package com.adidas.pac.processor;

import com.adidas.pac.model.input.PaymentRules;
import com.adidas.pac.persistence.PaymentRulesDataStructure;
import com.fasterxml.jackson.databind.JsonNode;

public class PaymentRulesProcessor {

  private static PaymentRulesProcessor instance;
  private PaymentRulesDataStructure paymentRulesDataStructure =
      PaymentRulesDataStructure.getInstance();

  private PaymentRulesProcessor() {}

  public static PaymentRulesProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentRulesProcessor();
    }
    return instance;
  }

  public void process(JsonNode paymentRulesNode) {
    try {
      Long maxLimit = paymentRulesNode.get("max-limit").asLong();
      PaymentRules paymentRules = PaymentRules.builder().maxLimit(maxLimit).build();
      paymentRulesDataStructure.save(paymentRules);
    } catch (Exception e) {

    }
  }
}
