package com.adidas.pac.processor;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LineProcessor {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final PaymentRulesProcessor paymentRulesProcessor =
      PaymentRulesProcessor.getInstance();
  private static final PaymentSessionProcessor paymentSessionProcessor =
      PaymentSessionProcessor.getInstance();

  private static LineProcessor instance;

  private LineProcessor() {}

  public static LineProcessor getInstance() {
    if (instance == null) {
      instance = new LineProcessor();
    }
    return instance;
  }

  public void process(String line) throws JsonParseException, JsonMappingException, IOException {
    if (!Objects.isNull(line) && !line.isEmpty() && !line.isBlank()) {
      JsonNode rootJsonNode = mapper.readTree(line);

      if (rootJsonNode != null) {
        JsonNode paymentRulesNode = rootJsonNode.get("payment-rules");
        JsonNode paymentSessionNode = rootJsonNode.get("payment-session");

        if (paymentRulesNode != null) {
          paymentRulesProcessor.process(paymentRulesNode);
        } else if (paymentSessionNode != null) {
          paymentSessionProcessor.process(mapper, paymentSessionNode);
        }
      }
    }
  }
}
