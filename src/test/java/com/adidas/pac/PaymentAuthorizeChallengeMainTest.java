package com.adidas.pac;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentAuthorizeChallengeMainTest {

  @Test
  public void testMainSuccess() {
    String data =
        "{\"payment-rules\": {\"max-limit\": 100}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087656}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087656, \"cc\": \"visa\", \"amount\": 90, \"time\": \"2022-02-12T10:00:00.000Z\"}}\r\n";

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

    assertTrue(Boolean.TRUE);
  }
}
