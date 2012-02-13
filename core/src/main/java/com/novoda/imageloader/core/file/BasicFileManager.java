package com.novoda.imageloader.core.file;

import java.io.File;

import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.network.UrlUtil;
import com.novoda.imageloader.core.service.CacheCleaner;

import android.content.Context;
import android.content.Intent;

public class BasicFileManager implements FileManager {

	private LoaderSettings settings;
	
	public BasicFileManager(LoaderSettings settings) {
		this.settings = settings;
	}
	
	@Override
	public void delete(Context context) {
		sendCacheCleanUpBroadcast(context, 0);
	}
	
	@Override
  public void clean(Context context) {
		long expirationPeriod = settings.getExpirationPeriod();
		sendCacheCleanUpBroadcast(context, expirationPeriod);
  }

	@Override
	public String getFilePath(String imageUrl) {
		File f = getFile(imageUrl);
		if (f.exists()) {
			return f.getAbsolutePath();
		}
		return null;
	}

	private void sendCacheCleanUpBroadcast(Context context, long expirationPeriod) {
		String path = settings.getCacheDir().getAbsolutePath();
		Intent i = CacheCleaner.getCleanCacheIntent(path, expirationPeriod);
		i.setPackage(context.getPackageName());
		context.startService(i);
	}

	@Override
  public boolean exists(String path) {
	  // TODO Auto-generated method stub
	  return false;
  }
	
	@Override
	public File getFile(String url) {
		url = processUrl(url);
		String filename = String.valueOf(url.hashCode());
		return new File(settings.getCacheDir(), filename);
	}
	
	private String processUrl(String url) {
		if (!settings.isQueryIncludedInHash()) {
			return url;
		}
		return new UrlUtil().removeQuery(url);
	}
	
}
