package com.adidas.pac.persistence;

import com.adidas.pac.model.input.PaymentAuthorization;

public class PaymentAuthorizationDataStructure
    extends InmutableDataStructure<PaymentAuthorization> {
  private static PaymentAuthorizationDataStructure instance;

  private PaymentAuthorizationDataStructure() {
    super();
  }

  public static PaymentAuthorizationDataStructure getInstance() {
    if (instance == null) {
      instance = new PaymentAuthorizationDataStructure();
    }
    return instance;
  }
}
