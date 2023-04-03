package com.adidas.pac.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentSessionOutputBody {
	@JsonProperty("payment-id")
	private Long paymentId;
	@JsonProperty("available-limit")
	private Integer availableLimit;
}
