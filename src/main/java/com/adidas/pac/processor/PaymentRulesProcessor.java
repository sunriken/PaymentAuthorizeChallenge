package com.adidas.pac.processor;

import com.adidas.pac.model.input.PaymentRules;
import com.adidas.pac.persistence.PaymentRulesDataStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

  public void process(ObjectMapper mapper, JsonNode paymentRulesNode) {
    try {
      PaymentRules paymentRules = mapper.treeToValue(paymentRulesNode, PaymentRules.class);
      paymentRulesDataStructure.save(paymentRules);
    } catch (JsonProcessingException e) {
    } catch (Exception e) {
    }
  }
}
