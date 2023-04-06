package com.adidas.pac.processor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import com.adidas.pac.exception.ExistingElementException;
import com.adidas.pac.model.input.PaymentAuthorization;
import com.adidas.pac.model.input.PaymentRules;
import com.adidas.pac.model.input.PaymentSession;
import com.adidas.pac.model.output.PaymentAuthorizationOutput;
import com.adidas.pac.model.output.PaymentAuthorizationOutputBody;
import com.adidas.pac.persistence.PaymentAuthorizationDataStructure;
import com.adidas.pac.persistence.PaymentRulesDataStructure;
import com.adidas.pac.persistence.PaymentSessionDataStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentAuthorizationProcessor {

  private static final String PAYMENT_SESSION_NOT_INITIALIZED = "paymentsession-not-initialized";
  private static final String PAYMENT_RULES_NOT_INITIALIZED = "paymentrules-not-initialized";
  private static final String INSUFFICIENT_LIMIT = "insufficient-limit";
  private static final String HIGH_FREQUENCY_SMALL_INTERVAL = "high-frequency-small-interval";
  private static final String DOUBLED_TRANSACTION = "doubled-transaction";
  private static final String UNKNOWN_ERROR = "unknown-error";

  private static final long MAX_MINUTES_BEFORE = 2;
  private static final long MAX_TRANSACTIONS_BEFORE = 3;
  private static final long MAX_SIMILAR_TRANSACTIONS = 1;

  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.systemDefault());

  private static PaymentAuthorizationProcessor instance;

  private PaymentRulesDataStructure paymentRulesDataStructure =
      PaymentRulesDataStructure.getInstance();
  private PaymentSessionDataStructure paymentSessionDataStructure =
      PaymentSessionDataStructure.getInstance();
  private PaymentAuthorizationDataStructure paymentAuthorizationDataStructure =
      PaymentAuthorizationDataStructure.getInstance();

  private PaymentAuthorizationProcessor() {}

  public static PaymentAuthorizationProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentAuthorizationProcessor();
    }
    return instance;
  }

  public void process(ObjectMapper mapper, JsonNode paymentAuthorizationNode) {
    PaymentAuthorization paymentAuthorization = null;
    Optional<PaymentSession> session = Optional.empty();
    ArrayList<String> violations = new ArrayList<>();
    try {
      paymentAuthorization =
          PaymentAuthorization.builder()
              .paymentId(paymentAuthorizationNode.get("payment-id").asLong())
              .cc(paymentAuthorizationNode.get("cc").asText())
              .amount(paymentAuthorizationNode.get("amount").asLong())
              .time(asDate(paymentAuthorizationNode.get("time").asText()))
              .build();

      PaymentRules rules = paymentRulesDataStructure.get();
      if (rules == null || rules.getMaxLimit() == null) {
        violations.add(PAYMENT_RULES_NOT_INITIALIZED);
      }

      final Long paymentId = paymentAuthorization.getPaymentId();
      session =
          paymentSessionDataStructure.list().stream()
              .filter(p -> paymentId.equals(p.getPaymentId()))
              .findAny();
      if (!session.isPresent()) {
        violations.add(PAYMENT_SESSION_NOT_INITIALIZED);
      } else {
        if (paymentAuthorization.getAmount() > session.get().getAvailableLimit()) {
          violations.add(INSUFFICIENT_LIMIT);
        }
      }
      final LocalDateTime paymentDate = paymentAuthorization.getTime();
      long previousTransactionsAmount =
          paymentAuthorizationDataStructure.list().stream()
              .filter(
                  p ->
                      ((p.getTime().isAfter(paymentDate.minusMinutes(MAX_MINUTES_BEFORE))
                              && p.getTime().isBefore(paymentDate))
                          || p.getTime().equals(paymentDate)))
              .count();

      if ((previousTransactionsAmount + 1) > MAX_TRANSACTIONS_BEFORE) {
        violations.add(HIGH_FREQUENCY_SMALL_INTERVAL);
      }

      final Long amount = paymentAuthorization.getAmount();
      final String cc = paymentAuthorization.getCc();
      long similarTransactions =
          paymentAuthorizationDataStructure.list().stream()
              .filter(
                  p ->
                      p.getAmount().equals(amount)
                          && p.getCc().equals(cc)
                          && ((p.getTime().isAfter(paymentDate.minusMinutes(MAX_MINUTES_BEFORE))
                                  && p.getTime().isBefore(paymentDate))
                              || p.getTime().equals(paymentDate)))
              .count();

      if (similarTransactions > MAX_SIMILAR_TRANSACTIONS) {
        violations.add(DOUBLED_TRANSACTION);
      }

      if (violations.isEmpty()) {
        paymentAuthorizationDataStructure.save(paymentAuthorization);
        session
            .get()
            .setAvailableLimit(
                session.get().getAvailableLimit() - paymentAuthorization.getAmount());
      }

    } catch (ExistingElementException e) {
      violations.add(DOUBLED_TRANSACTION);
    } catch (Exception e) {
      violations.add(UNKNOWN_ERROR);
    }

    try {
      String[] violationsArray = new String[violations.size()];
      violationsArray = violations.toArray(violationsArray);
      PaymentAuthorizationOutput output =
          PaymentAuthorizationOutput.builder()
              .paymentAuthorization(
                  PaymentAuthorizationOutputBody.builder()
                      .paymentId(
                          paymentAuthorization != null ? paymentAuthorization.getPaymentId() : null)
                      .availableLimit(!session.isEmpty() ? session.get().getAvailableLimit() : null)
                      .build())
              .violations(violationsArray)
              .build();
      String outputLine = mapper.writeValueAsString(output);
      System.out.println(outputLine);
    } catch (JsonProcessingException e) {

    }
  }

  private LocalDateTime asDate(String dateString) {
    LocalDateTime date = LocalDateTime.parse(dateString, formatter);
    return date;
  }
}
