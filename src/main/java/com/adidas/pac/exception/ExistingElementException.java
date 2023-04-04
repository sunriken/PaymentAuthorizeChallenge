package com.adidas.pac.exception;

public class ExistingElementException extends RuntimeException {
  private static final long serialVersionUID = -802561215434425463L;

  public ExistingElementException() {
    super();
  }

  public ExistingElementException(Throwable cause) {
    super(cause);
  }

  public ExistingElementException(String message) {
    super(message);
  }

  public ExistingElementException(String message, Throwable cause) {
    super(message, cause);
  }
}
