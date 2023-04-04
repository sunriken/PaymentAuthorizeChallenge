package com.adidas.pac.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NonNull;

@Builder
@Data
public class PaymentSession {
  @JsonProperty("payment-id")
  @NonNull
  private final Long paymentId;

  @JsonProperty("available-limit")
  @Exclude
  private Long availableLimit;
}
