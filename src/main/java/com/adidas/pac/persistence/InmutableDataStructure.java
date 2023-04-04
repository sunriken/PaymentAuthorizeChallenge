package com.adidas.pac.persistence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.adidas.pac.exception.ExistingElementException;

public class InmutableDataStructure<T extends Object> {
  private final List<T> list = new LinkedList<T>();

  public List<T> list() {
    return Collections.unmodifiableList(list);
  }

  public synchronized void save(T element) {
    if (list.contains(element)) {
      throw new ExistingElementException("Element exists inside the list");
    } else {
      list.add(element);
    }
  }
}
