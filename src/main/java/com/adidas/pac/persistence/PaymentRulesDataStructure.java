package com.adidas.pac.persistence;

import com.adidas.pac.model.input.PaymentRules;

public class PaymentRulesDataStructure {
  private static PaymentRulesDataStructure instance;
  private PaymentRules paymentRules;

  private PaymentRulesDataStructure() {
    this.paymentRules = null;
  }

  public static PaymentRulesDataStructure getInstance() {
    if (instance == null) {
      instance = new PaymentRulesDataStructure();
    }
    return instance;
  }

  public synchronized void save(PaymentRules paymentRules) {
    this.paymentRules = paymentRules;
  }

  public synchronized PaymentRules get() {
    return this.paymentRules;
  }
}
