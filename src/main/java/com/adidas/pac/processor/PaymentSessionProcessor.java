package com.adidas.pac.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.adidas.pac.exception.ExistingElementException;
import com.adidas.pac.model.input.PaymentSession;
import com.adidas.pac.model.output.PaymentSessionOutput;
import com.adidas.pac.model.output.PaymentSessionOutputBody;
import com.adidas.pac.persistence.PaymentRulesDataStructure;
import com.adidas.pac.persistence.PaymentSessionDataStructure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentSessionProcessor {

  private static PaymentSessionProcessor instance;
  private PaymentAuthorizationProcessor paymentAuthorizationProcessor =
      PaymentAuthorizationProcessor.getInstance();
  private PaymentSessionDataStructure paymentSessionDataStructure =
      PaymentSessionDataStructure.getInstance();
  private PaymentRulesDataStructure paymentRulesDataStructure =
      PaymentRulesDataStructure.getInstance();

  private static final String PAYMENT_SESSION_ALREADY_INITIALIZED =
      "paymentsession-already-initialized";
  private static final String PAYMENT_RULES_NOT_INITIALIZED = "paymentrules-not-initialized";
  private static final String JSON_PROCESSING_ERROR = "json-processing-error";
  private static final String OTHER_ERROR = "other-error";

  private PaymentSessionProcessor() {}

  public static PaymentSessionProcessor getInstance() {
    if (instance == null) {
      instance = new PaymentSessionProcessor();
    }
    return instance;
  }

  public void process(ObjectMapper mapper, JsonNode paymentSessionNode) {
    List<String> violations = new ArrayList<String>();
    PaymentSession paymentSession = null;
    try {
      JsonNode ccNode = paymentSessionNode.get("cc");
      if (ccNode != null) {
        paymentAuthorizationProcessor.process(mapper, paymentSessionNode);
      } else {
        if (paymentRulesDataStructure.get() == null) {
          violations.add(PAYMENT_RULES_NOT_INITIALIZED);
        }
        paymentSession = mapper.treeToValue(paymentSessionNode, PaymentSession.class);
        paymentSession.setAvailableLimit(paymentRulesDataStructure.get().getMaxLimit());
        if (violations.size() == 0) {
          paymentSessionDataStructure.save(paymentSession);
        }
      }
    } catch (JsonProcessingException e) {
      violations.add(JSON_PROCESSING_ERROR);
    } catch (ExistingElementException e) {
      violations.add(PAYMENT_SESSION_ALREADY_INITIALIZED);
    } catch (Exception e) {
      violations.add(OTHER_ERROR);
    }
    try {
      String[] violationsArray = new String[violations.size()];
      violationsArray = violations.toArray(violationsArray);
      PaymentSessionOutput output =
          PaymentSessionOutput.builder()
              .paymentSession(
                  PaymentSessionOutputBody.builder()
                      .availableLimit(
                          !Objects.isNull(paymentRulesDataStructure.get())
                              ? paymentRulesDataStructure.get().getMaxLimit()
                              : null)
                      .paymentId(
                          !Objects.isNull(paymentSession) ? paymentSession.getPaymentId() : null)
                      .build())
              .violations(violationsArray)
              .build();
      String out = mapper.writeValueAsString(output);
      System.out.println(out);
    } catch (JsonProcessingException f) {
    }
  }
}
