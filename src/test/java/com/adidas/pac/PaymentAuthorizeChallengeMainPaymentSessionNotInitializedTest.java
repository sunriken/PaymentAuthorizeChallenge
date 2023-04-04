package com.adidas.pac;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class PaymentAuthorizeChallengeMainPaymentSessionNotInitializedTest {

  @Test
  public void testMainPaymentSessionNotInitialized() {
    String data =
        "{\"payment-session\": {\"payment-id\": 89087654, \"cc\": \"visa\", \"amount\": 120, \"time\": \"2022-02-13T10:00:00.000Z\"}}\r\n";

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
