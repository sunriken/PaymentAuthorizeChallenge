package com.adidas.pac.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentAuthorizationOutputBody {
  @JsonProperty("payment-id")
  private Long paymentId;

  @JsonProperty("available-limit")
  private Long availableLimit;
}
