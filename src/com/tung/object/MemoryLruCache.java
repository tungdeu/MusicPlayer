package com.tung.object;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryLruCache {

	private LruCache<String, Bitmap> mMemoryCache;

	public MemoryLruCache() {
		buildingLruCache();
	}

	private void buildingLruCache() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 12)
					return bitmap.getByteCount() / 1024;
				
				else
					return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
			}
		};
	}

	public Bitmap loadBitmap(String res) {
		final String imageKey = res;
		final Bitmap bitmap = getBitmapFromMemCache(imageKey);
		return bitmap;
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
}
