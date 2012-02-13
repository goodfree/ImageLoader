package com.novoda.imageloader.core.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;


import android.graphics.Bitmap;

public class SoftMapCache implements CacheManager {

  private HashMap<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();

  @Override
  public boolean hasBitmap(String url) {
    if (!cache.containsKey(url)) {
      return false;
    }
    if (cache.get(url) == null) {
      return false;
    }
    return true;
  }

  @Override
  public Bitmap get(String url) {
    SoftReference<Bitmap> bmpr = cache.get(url);
    if (bmpr == null) {
      return null;
    }
    return bmpr.get();
  }

  @Override
  public void put(String url, Bitmap bmp) {
    cache.put(url, new SoftReference<Bitmap>(bmp));
  }

  @Override
  public void clean() {
    cache.clear();
  }

}
