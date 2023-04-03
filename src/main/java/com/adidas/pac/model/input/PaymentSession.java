package com.adidas.pac.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class PaymentSession {
	@JsonProperty("payment-id")
	private Long paymentId;
}
