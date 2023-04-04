package com.adidas.pac.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentSessionOutput {
  @JsonProperty("payment-session")
  private PaymentSessionOutputBody paymentSession;

  @JsonProperty("violations")
  private String[] violations;
}
