package com.adidas.pac;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class PaymentAuthorizeChallengeMainTest {

  @Test
  public void testMainEmptyLines() {
    String data = "\r\n";
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
