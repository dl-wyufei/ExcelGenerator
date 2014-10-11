package com.kunpeng.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HeadData {

  private HashMap<String, List<String>> headers = null;

  public HeadData() {
    headers = new HashMap<String, List<String>>();
  }

  public void addHeaders(String groupName, Set<String> keys) {
    if (!headers.containsKey(groupName)) {
      headers.put(groupName, new ArrayList<String>(keys));
    }
  }

  public List<String> getHeaders(String groupName) {
    if (headers.containsKey(groupName)) {
      return Collections.unmodifiableList(headers.get(groupName));
    }
    return null;
  }

  public Set<String> keySet() {
    return headers.keySet();
  }
}
