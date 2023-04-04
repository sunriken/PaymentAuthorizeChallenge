package com.adidas.pac;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class PaymentAuthorizeChallengeMainPaymentRulesNotInitializedTest {
  @Test
  public void testMainPaymentRulesNotInitialized() {
    String data = "{\"payment-session\": {\"payment-id\": 89087653}}\r\n";

    InputStream stdin = System.in;
    try {
      System.setIn(new ByteArrayInputStream(data.getBytes()));
      assertDoesNotThrow(
          () -> {
            PaymentAuthorizeChallengeMain.main(new String[0]);
          });
    } finally {
      System.setIn(stdin);
    }
  }
}
