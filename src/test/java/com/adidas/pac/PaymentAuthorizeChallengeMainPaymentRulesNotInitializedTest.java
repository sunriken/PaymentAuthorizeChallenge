package com.adidas.pac;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

@Isolated
@Order(1)
public class PaymentAuthorizeChallengeMainPaymentRulesNotInitializedTest {

  @Test
  public void testMainPaymentRulesNotInitialized() {
    String data = "{\"payment-session\": {\"payment-id\": 89087653}}\r\n";

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
      assertTrue(systemOutText.contains("paymentrules-not-initialized"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }
  }
}
