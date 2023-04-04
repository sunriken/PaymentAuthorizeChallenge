package com.adidas.pac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.adidas.pac.processor.LineProcessor;

public class PaymentAuthorizeChallengeMain {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line = null;
      LineProcessor processor = LineProcessor.getInstance();

      while ((line = br.readLine()) != null) {
        processor.process(line);
      }
    } catch (IOException ioe) {
    }
  }
}
