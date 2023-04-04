package com.adidas.pac;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

@Isolated
public class PaymentAuthorizeChallengeMainPaymentSessionNotInitializedTest {

  @Test
  public void testMainPaymentSessionNotInitialized() {
    String data =
        "{\"payment-session\": {\"payment-id\": 89087654, \"cc\": \"visa\", \"amount\": 120, \"time\": \"2022-02-13T10:00:00.000Z\"}}\r\n";

    InputStream stdin = System.in;
    try {

      System.setIn(new ByteArrayInputStream(data.getBytes()));
      String systemOutText =
          tapSystemOut(
              () -> {
                assertDoesNotThrow(
                    () -> {
                      PaymentAuthorizeChallengeMain.main(new String[0]);
                    });
              });
      assertTrue(systemOutText.contains("paymentsession-not-initialized"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }
  }
}
