package com.kunpeng;

import java.util.List;

public final class Utils {

  public static <T> void addDistinctElement(List<T> results, List<T> source) {
    
    if (results != null && source != null) {
      for (T t : source) {
        if (!results.contains(t)) {
          results.add(t);
        }
      }
    }
    
  }

}
