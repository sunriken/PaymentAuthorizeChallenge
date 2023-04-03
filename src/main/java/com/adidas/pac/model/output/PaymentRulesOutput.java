package com.adidas.pac.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentRulesOutput {
  @JsonProperty("payment-rules")
  private PaymentRulesOutputBody paymentRules;
}
