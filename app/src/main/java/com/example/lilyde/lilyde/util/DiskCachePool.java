package com.example.lilyde.lilyde.util;

import android.util.Log;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主要为了解决glide自定义diskcache的问题
 */
public class DiskCachePool {
//    private Map<String, DiskLruCacheFactory> diskCacheFatoryMap;
//    private Map<String,DiskCache> diskCacheMap;
//    private int DISK_SIZE = 100*1024*1024;
//
//
//    public void addDiskCacheFactory(String folderName){
//        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(folderName,DISK_SIZE);
//        diskCacheFatoryMap.put(folderName,diskLruCacheFactory);
//    }
//
//    public void addDiskCache(String diskName,String folderName){
//        if(diskCacheFatoryMap.get(folderName) == null){
//            Log.e("lilyNull","null");
//        }
//        diskCacheMap.put(diskName,diskCacheFatoryMap.get(folderName).build());
//
//    }
//
//    public DiskCache.Factory get(String name){
//        return diskCacheFatoryMap.get(name);
//    }
//
//    public void DiskLruCacheBuild(String folderName){
//        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(folderName,DISK_SIZE);
//
//    }


    private final Map<String, DiskCache> mDiskCaches = new ConcurrentHashMap<>();
    private final Map<String, DiskCache.Factory> mDiskCacheFactories = new ConcurrentHashMap<>();
    private final FolderDiskCacheFactory mDefaultFactory;

    public DiskCachePool(FolderDiskCacheFactory defaultFactory) {
        mDefaultFactory = defaultFactory;
    }

    public void add(DiskCache.Factory factory, String name) {
        mDiskCacheFactories.put(name, factory);
    }

    public DiskCache get(String name) {
        DiskCache cache = mDiskCaches.get(name);

        if (cache != null) {
            return cache;
        }

        return createDiskCache(name);
    }

    private DiskCache createDiskCache(String name) {
        DiskCache.Factory factory = findDiskCacheFactory(name);

        if (factory == null) {
            return new DiskCacheAdapter();
        }

        DiskCache cache = factory.build();
        mDiskCaches.put(name, cache);
        return cache;
    }

    private DiskCache.Factory findDiskCacheFactory(String name) {
        DiskCache.Factory factory = mDiskCacheFactories.get(name);

        if (factory != null) {
            return factory;
        }

        if (mDefaultFactory != null) {
            return mDefaultFactory.create(name);
        }

        return null;
    }

    public interface FolderDiskCacheFactory {
        DiskCache.Factory create(String folderName);
    }


}
