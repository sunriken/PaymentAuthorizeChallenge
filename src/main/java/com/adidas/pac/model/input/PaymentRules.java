package com.adidas.pac.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentRules {
  @JsonProperty("max-limit")
  private Integer maxLimit;
}
