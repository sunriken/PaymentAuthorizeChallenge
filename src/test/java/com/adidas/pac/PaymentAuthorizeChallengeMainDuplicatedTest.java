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
public class PaymentAuthorizeChallengeMainDuplicatedTest {

  @Test
  public void testMainDuplicated() {
    String data =
        "{\"payment-rules\": {\"max-limit\": 100}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087657}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087657, \"cc\": \"visa\", \"amount\": 90, \"time\": \"2022-02-13T10:00:00.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087658}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087658, \"cc\": \"visa\", \"amount\": 90, \"time\": \"2022-02-13T10:01:00.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087659}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087659, \"cc\": \"visa\", \"amount\": 90, \"time\": \"2022-02-13T10:01:30.000Z\"}}\r\n";

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
      assertTrue(systemOutText.contains("doubled-transaction"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }

    assertTrue(Boolean.TRUE);
  }

  @Test
  public void testMainDuplicatedExactMatch() {
    String data =
        "{\"payment-rules\": {\"max-limit\": 100}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087632}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087632, \"cc\": \"mastercard\", \"amount\": 10, \"time\": \"2022-02-13T10:00:00.000Z\"}}\r\n"
            + "{\"payment-session\": {\"payment-id\": 89087632, \"cc\": \"mastercard\", \"amount\": 10, \"time\": \"2022-02-13T10:00:00.000Z\"}}\r\n";

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
      assertTrue(systemOutText.contains("doubled-transaction"));
    } catch (Exception e) {
      fail(e);
    } finally {
      System.setIn(stdin);
    }

    assertTrue(Boolean.TRUE);
  }
}
