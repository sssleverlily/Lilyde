package com.example.lilyde.progress

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.example.lilyde.http.OkHttpUrlLoader
import com.example.lilyde.lilyde.util.FrameSequenceDecoder
import com.example.lilyde.lilyde.util.FrameSequenceDrawable
import java.io.InputStream

/**
 * 自定义glidemodule
 */
@GlideModule(glideName = "LilydeApp")
class LilydeModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, "Glide", IMAGE_DISK_CACHE_MAX_SIZE.toLong()))
        //设置Glide内存缓存大小
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        //要用默认值作为基准，然后调整它。
        // 比如，如果你认为你的 app 需要 20% 大的缓存作为 Glide 的默认值，用我们上面的变量去计算他们
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(ProgressManager.okHttpClient))
        registry.prepend(Registry.BUCKET_GIF, InputStream::class.java, FrameSequenceDrawable::class.java, FrameSequenceDecoder(glide.bitmapPool))

    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        /**
         * 图片缓存文件最大值为100Mb
         */
        const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024
    }
}