package com.example.lilyde.lilyde.cache

import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter
import com.example.lilyde.Lilyde.clearMemory
import java.util.concurrent.ConcurrentHashMap

class DiskCachePool {

    private var mDiskCaches = ConcurrentHashMap<String, DiskCache>()
    private var mDiskCacheFactories = ConcurrentHashMap<String, DiskCache.Factory>()
    private lateinit var mDefaultFactory: FolderDiskCacheFactory

    fun DiskCachePool(folderName: String): DiskCache.Factory {
       return mDefaultFactory.create(folderName)
    }

    fun add(factory: DiskCache.Factory, name: String) {
        mDiskCacheFactories[name] = factory
    }

    operator fun get(name: String): DiskCache? {
        val cache = mDiskCaches[name]

        return cache ?: createDiskCache(name)

    }

    private fun createDiskCache(name: String): DiskCache? {
        val factory = findDiskCacheFactory(name) ?: return DiskCacheAdapter()

        val cache = factory.build()
        mDiskCaches.put(name,cache!!)
        return cache
    }

    private fun findDiskCacheFactory(name: String): DiskCache.Factory? {
        val factory = mDiskCacheFactories[name]

        if (factory != null) {
            return factory
        }

        return mDefaultFactory.create(name)

    }

    interface FolderDiskCacheFactory {
        fun create(folderName: String): DiskCache.Factory
    }

}