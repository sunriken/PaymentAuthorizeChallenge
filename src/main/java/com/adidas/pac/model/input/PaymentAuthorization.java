package com.adidas.pac.model.input;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentAuthorization {
  @JsonProperty("payment-id")
  private Long paymentId;

  @JsonProperty("cc")
  private String cc;

  @JsonProperty("amount")
  private Long amount;

  @JsonProperty("time")
  private LocalDateTime time;
}
