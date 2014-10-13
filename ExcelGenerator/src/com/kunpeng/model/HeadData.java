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
    ArrayList<String> multiHeader = new ArrayList<String>(1);
    multiHeader.add("机型");
    multiHeader.addAll(keys);
    if (!headers.containsKey(groupName)) {
      headers.put(groupName, new ArrayList<String>(multiHeader));
    }
  }

  public void addHeaders(String groupName, String key) {
    ArrayList<String> singleHeader = new ArrayList<String>(1);
    singleHeader.add("机型");
    singleHeader.add(key);
    if (!headers.containsKey(groupName)) {
      headers.put(groupName, new ArrayList<String>(singleHeader));
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
