package com.adidas.pac.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PaymentAuthorizationOutput {
	@JsonProperty("payment-session")
	private PaymentAuthorizationOutputBody paymentAuthorization;
	@JsonProperty("violations")
	private String[] violations;
}
