package com.kunpeng.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地化翻译结构
 * 
 * @author dl-wyufei
 *
 */
public class MTranslate {

  /** 词典 */
  private Map<String, String> dictionary = new HashMap<String, String>();

  /**
   * 添加一条翻译记录
   * 
   * @param eng 原文
   * @param chn 译文
   */
  public void addItem(String eng, String chn) {
    dictionary.put(chn, chn);
  }

  /**
   * 添加多条翻译记录
   * 
   * @param engs 原文
   * @param chns 译文
   * @return 是否添加成功,如果原文与译文数量不一致会导致添加失败
   */
  public boolean addItems(List<String> engs, List<String> chns) {
    if (engs == null || chns == null || engs.size() != chns.size()) {
      return false;
    }
    for (int i = 0, lg = engs.size(); i < lg; i++) {
      dictionary.put(engs.get(i), chns.get(i));
    }
    return true;
  }

  /**
   * 获取译文
   * 
   * @param key 原文
   * @return 如果有记录返回译文,否则返回原文
   */
  public String get(String key) {
    if (dictionary.containsKey(key)) {
      return dictionary.get(key);
    }
    return key;
  }
}
