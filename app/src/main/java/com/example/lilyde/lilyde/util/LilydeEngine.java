package com.example.lilyde.lilyde.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Observable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.request.Request;
import com.facebook.common.memory.ByteArrayPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import rx.Scheduler;

import static com.bumptech.glide.util.ByteBufferUtil.fromFile;
import static com.bumptech.glide.util.ByteBufferUtil.toStream;

public class LilydeEngine {
    private static final String TAG = "LilydeEngine";

    public void aa(){

    }
//    private static final int DEFAULT_DATA_CACHE_SIZE = 100 * 1024 * 1024; //100M
//    private static final int DEFAULT_RESOURCE_CACHE_SIZE = 100 * 1024 * 1024; //100M
//
//    private static final String DATA_CACHE_NAME = "dataImage";
//    private static final String RESOURCE_CACHE_NAME = "resourceImage";
//
//    private final BitmapPool mBitmapPool;
//    private final MemoryCache mMemoryCache;
//    private final ByteArrayPool mByteArrayPool;
//    private final NetworkLoader mNetworkLoader;
//    private final DiskCachePool mDiskCachePool;
//    private final BitmapDecoder mBitmapDecoder;
//    private final ContentResolver mContentResolver;
//    private final ContainerMonitor mContainerMonitor = new ContainerMonitor();
//
//    private final Scheduler mIOScheduler;
//    private final Scheduler mNWScheduler;
//
//    private FetchCallback mFetchCallback;
//    private final Context mApplicationContext;
//
//    LilydeEngine(Context context,
//           Scheduler ioScheduler,
//           Scheduler networkScheduler,
//           OkHttpClient httpClient,
//           DiskCachePool.FolderDiskCacheFactory folderDiskCacheFactory,
//           @NonNull FetchCallback callback) {
//
//        mApplicationContext = context.getApplicationContext();
//
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
//                .build();
//
//        mMemoryCache = new LruResourceCache(calculator.getMemoryCacheSize());
//        mMemoryCache.setResourceRemovedListener(
//                new MemoryCache.ResourceRemovedListener() {
//                    @Override
//                    public void onResourceRemoved(Resource<?> removed) {
//                        DLog.d(TAG, "onResourceRemoved");
//                        removed.recycle();
//                    }
//                });
//
//        final LruBitmapPool.OOMTracker oomTracker = new LruBitmapPool.OOMTracker() {
//            @Override
//            public void onOOMCatch(OutOfMemoryError e) {
//                clearMemory();
//
//                if (mFetchCallback != null) {
//                    mFetchCallback.onOOMCatch(e, false);
//                }
//            }
//
//            @Override
//            public void onOOMCatchAgain(OutOfMemoryError e) {
//                if (mFetchCallback != null) {
//                    mFetchCallback.onOOMCatch(e, true);
//                }
//            }
//        };
//
//        LruBitmapPool lruBitmapPool = new LruBitmapPool(calculator.getBitmapPoolSize());
//        lruBitmapPool.setOOMTracker(oomTracker);
//
//        mBitmapPool = lruBitmapPool;
//
//        mByteArrayPool = new LruByteArrayPool(calculator.getByteArrayPoolSize());
//
//        if (httpClient != null) {
//            mNetworkLoader = new NetworkLoader(mByteArrayPool, httpClient);
//        } else {
//            mNetworkLoader = new NetworkLoader(mByteArrayPool);
//        }
//
//        mDiskCachePool = new DiskCachePool(folderDiskCacheFactory);
//
//        DiskCache.Factory diskCacheFactory = new InternalCacheDiskCacheFactory(context,
//                RESOURCE_CACHE_NAME, DEFAULT_RESOURCE_CACHE_SIZE);
//        mDiskCachePool.add(diskCacheFactory, RESOURCE_CACHE_NAME);
//
//        diskCacheFactory = new InternalCacheDiskCacheFactory(context, DATA_CACHE_NAME,
//                DEFAULT_DATA_CACHE_SIZE);
//        mDiskCachePool.add(diskCacheFactory, DATA_CACHE_NAME);
//
//        Downsampler downsampler = new Downsampler(context.getResources().getDisplayMetrics(),
//                mBitmapPool, mByteArrayPool,
//                new BitmapResource.BitmapRemovedListener() {
//                    @Override
//                    public void onBitmapRemoved(BitmapResource resource) {
//                        DLog.d(TAG, "Bitmap resource recycle");
//                        mBitmapPool.put(resource.get());
//                    }
//                });
//        mBitmapDecoder = new BitmapDecoder(downsampler);
//
//        mContentResolver = context.getApplicationContext().getContentResolver();
//
//        if (ioScheduler != null) {
//            mIOScheduler = ioScheduler;
//        } else {
//            mIOScheduler = Schedulers.createScheduler("Diamond_IO", 2, 8);
//        }
//
//        if (networkScheduler != null) {
//            mNWScheduler = networkScheduler;
//        } else {
//            mNWScheduler = Schedulers.createScheduler("Diamond_Network", 2, 8);
//        }
//
//        mFetchCallback = callback;
//    }
//
//    //private static Dispatcher dispatcher = new FIFODispatcher(5);
//
//    /**
//     * 发起请求。
//     *
//     * @param request
//     * @param fetcherBuilder
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<Response<T>> load(Request<T> request, FetcherBuilder<T> fetcherBuilder) {
//        Resource<T> resource = (Resource<T>) mMemoryCache.get(request.getKey());
//        if (resource != null) {
//            resource.acquire();
//            if (mFetchCallback != null) {
//                mFetchCallback.onSuccess(DataSource.MEMORY_CACHE);
//            }
//            return Observable.just(Response.complete(resource, DataSource.MEMORY_CACHE));
//        }
//
//        return fetcherBuilder.build(request).fetch()
//                .doOnNext(new Action1<Response<T>>() {
//                    @Override
//                    public void call(Response<T> tResponse) {
//                        if (tResponse.getState() == Response.State.COMPLETE
//                                && mFetchCallback != null) {
//                            mFetchCallback.onSuccess(tResponse.getDataSource());
//                        }
//                    }
//                })
//                .doOnError(new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        if (!(throwable instanceof EmptyException)
//                                && mFetchCallback != null) {
//                            mFetchCallback.onError(throwable);
//                        }
//                    }
//                })
//                /*.compose(new DispatchTransformer<Response<T>>(dispatcher, request.getKey()))*/;
//    }
//
//    /**
//     * 直接从内存获取资源，如果内存没有命中，则返回null。
//     *
//     * @param request
//     * @param <T>
//     * @return
//     */
//    public <T> Response<T> loadFromMemory(Request<T> request) {
//        Resource<T> resource = (Resource<T>) mMemoryCache.get(request.getKey());
//        if (resource != null) {
//            resource.acquire();
//            return Response.complete(resource, DataSource.MEMORY_CACHE);
//        }
//
//        return null;
//    }
//
//    /**
//     * 将本地的文件移动到disk cache中。
//     *
//     * @param localPath
//     * @param key
//     * @param diskCacheName
//     */
//    public void moveFileToDataDiskCache(String localPath, Key key, String diskCacheName) {
//        DiskCache cache = getDiskCache(
//                (diskCacheName == null || diskCacheName.length() == 0)
//                        ? DATA_CACHE_NAME
//                        : diskCacheName);
//
//        InputStream inputStream = null;
//        byte[] buffer = mByteArrayPool.get(8 * 1024);
//        try {
//            inputStream = toStream(fromFile(new File(localPath)));
//            cache.put(key, new NetworkLoader.NetworkWriter(inputStream, buffer));
//        } catch (IOException e) {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        } finally {
//            mByteArrayPool.put(buffer);
//        }
//    }
//
//    /**
//     * 计算磁盘缓存大小。
//     * @return 缓存大小，单位为字节
//     */
//    public long sizeOfDiskCache() {
//        return getDiskCache(DATA_CACHE_NAME).size() + getDiskCache(RESOURCE_CACHE_NAME).size();
//    }
//
//    /**
//     * 清除磁盘缓存。
//     */
//    public void clearDiskCache() {
//        getDiskCache(DATA_CACHE_NAME).clear();
//        getDiskCache(RESOURCE_CACHE_NAME).clear();
//    }
//
//    public void trimMemory(int level) {
//        mBitmapPool.trimMemory(level);
//        mMemoryCache.trimMemory(level);
//        mByteArrayPool.trimMemory(level);
//    }
//
//    public void clearMemory() {
//        mBitmapPool.clearMemory();
//        mMemoryCache.clearMemory();
//        mByteArrayPool.clearMemory();
//    }
//
//    public long getCacheSize() {
//        return mMemoryCache.getCurrentSize()
//                + mBitmapPool.getCurrentSize()
//                + mByteArrayPool.getCurrentSize();
//    }
//
//    public MemoryCache getMemoryCache() {
//        return mMemoryCache;
//    }
//
//    public BitmapPool getBitmapPool() {
//        return mBitmapPool;
//    }
//
//    public ByteArrayPool getByteArrayPool() {
//        return mByteArrayPool;
//    }
//
//    public Scheduler getMainScheduler() {
//        return AndroidSchedulers.mainThread();
//    }
//
//    public Scheduler getIOScheduler(Request request) {
//        return request.getIOScheduler() != null ? request.getIOScheduler() : mIOScheduler;
//    }
//
//    public Scheduler getNetworkScheduler() {
//        return mNWScheduler;
//    }
//
//    void addDiskCacheFactory(Map<String, DiskCache.Factory> factories) {
//        for (Map.Entry<String, DiskCache.Factory> entry : factories.entrySet()) {
//            mDiskCachePool.add(entry.getValue(), entry.getKey());
//        }
//    }
//
//    public DiskCache getResourceDiskCache() {
//        return mDiskCachePool.get(RESOURCE_CACHE_NAME);
//    }
//
//    public DiskCache getDataDiskCache(Request request) {
//        return request.getDiskDataCache() == null
//                ? mDiskCachePool.get(DATA_CACHE_NAME)
//                : mDiskCachePool.get(request.getDiskDataCache());
//    }
//
//    public DiskCache getDiskCache(String name) {
//        return mDiskCachePool.get(name);
//    }
//
//    public BitmapDecoder getBitmapDecoder() {
//        return mBitmapDecoder;
//    }
//
//    public NetworkLoader getNetworkLoader() {
//        return mNetworkLoader;
//    }
//
//    public ContainerMonitor getContainerMonitor() {
//        return mContainerMonitor;
//    }
//
//    public ContentResolver getContentResolver() {
//        return mContentResolver;
//    }
//
//    public Context getApplicationContext() {
//        return mApplicationContext;
//    }
//
//    public interface FetchCallback {
//        /**
//         * 拉取资源成功。
//         *
//         * @param source
//         */
//        void onSuccess(DataSource source);
//
//        /**
//         * 拉取资源失败。
//         *
//         * @param e
//         */
//        void onError(Throwable e);
//
//        /**
//         * 解码图片时出现OOM。
//         *
//         * @param e
//         * @param shouldCrash
//         */
//        void onOOMCatch(Throwable e, boolean shouldCrash);
//    }
}
