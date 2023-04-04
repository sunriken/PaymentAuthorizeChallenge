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
public class PaymentAuthorizeChallengeMainPaymentSessionAlreadyInitializedTest {

  @Test
  public void testMainPaymentSessionAlreadyInitialized() {
    String data =
        "{\"payment-rules\": {\"max-limit\": 100}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087646}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087646}}\r\n";

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
      assertTrue(systemOutText.contains("payment-session-already-initialized"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }
  }
}
