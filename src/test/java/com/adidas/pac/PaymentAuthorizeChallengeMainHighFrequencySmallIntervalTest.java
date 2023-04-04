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
public class PaymentAuthorizeChallengeMainHighFrequencySmallIntervalTest {
  @Test
  public void testMainHighFrequencySmallInterval() {
    String data =
        "{\"payment-rules\": {\"max-limit\": 100}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087660}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087660, \"cc\": \"visa\", \"amount\": 90, \"time\": \"2022-02-14T10:00:00.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087661}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087661, \"cc\": \"mastercard\", \"amount\": 80, \"time\": \"2022-02-14T10:01:00.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087662}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087662, \"cc\": \"visa\", \"amount\": 70, \"time\": \"2022-02-14T10:01:30.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087663}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087663, \"cc\": \"mastercard\", \"amount\": 60, \"time\": \"2022-02-14T10:01:35.000Z\"}}\r\n";

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
      assertTrue(systemOutText.contains("high-frequency-small-interval"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }

    assertTrue(Boolean.TRUE);
  }
}
