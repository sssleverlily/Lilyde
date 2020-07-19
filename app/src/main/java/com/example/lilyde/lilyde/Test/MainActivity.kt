package com.example.lilyde.lilyde.Test

import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.lilyde.Lilyde
import com.example.lilyde.R
import kotlinx.android.synthetic.main.activity_main.*
import com.example.lilyde.Lilyde.downloadImageToGallery
import com.example.lilyde.Lilyde.loadBlurImage
import com.example.lilyde.Lilyde.loadBorderImage
import com.example.lilyde.Lilyde.loadCircleImage
import com.example.lilyde.Lilyde.loadCircleWithBorderImage
import com.example.lilyde.Lilyde.loadGrayImage
import com.example.lilyde.Lilyde.loadImage
import com.example.lilyde.Lilyde.loadImageWithTransformation
import com.example.lilyde.Lilyde.loadResizeXYImage
import com.example.lilyde.Lilyde.loadRoundCornerImage
import com.example.lilyde.Lilyde.clearLoadImage
import com.example.lilyde.lilyde.util.FrameSequenceDrawable
import com.example.lilyde.progress.OnProgressListener
import com.example.lilyde.transformation.BlurTransformation
import com.example.lilyde.transformation.GrayscaleTransformation
import com.example.lilyde.transformation.RoundedCornersTransformation
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.squareup.picasso.Picasso
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class MainActivity : AppCompatActivity(){
    var url1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url2 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url3 = "http://img.mp.itc.cn/upload/20170311/48180d37e4474628900d058f3cc5ee7d_th.gif"
    var url4 = "http://img.mp.itc.cn/upload/20170311/33f2b7f7ffb04ecb81e42405e20b3fdc_th.gif"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        FrescoTest()
        glideTest()
        lilydeTest()
        picassoTest()

        //注解
        Glide.with(this)
                .asGif()
                .load(url3)
                .into(iv_3)
        //circleProgressView.visibility = View.VISIBLE

//        iv_0.loadImage(this, url3,onProgressListener = object : OnProgressListener {
//            override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) {
//                // 跟踪进度
//                if (isComplete) {
//                    //网络太快看不见进度条，先设置看见
//                    circleProgressView.visibility = View.VISIBLE
//                }
//                circleProgressView.progress = percentage
//            }
//        })

//        val animator = ValueAnimator.ofInt(0, 100)
//        animator.duration = 2000
//        animator.repeatCount = ValueAnimator.INFINITE
//        animator.interpolator = LinearInterpolator()
//        animator.addUpdateListener { circleProgressView.progress = (animator.animatedValue as Int) }
//        animator.start()
        Lilyde.placeHolderImageView = R.color.red
        Lilyde.circlePlaceholderImageView = R.color.red
//        iv_1.setOnClickListener { downloadImage() }
//        iv_1.loadImage(this, url3)
//        iv_2.loadImage(this, url4, requestListener = object : RequestListener<Drawable?> {
//            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
//                Toast.makeText(application, R.string.load_failed, Toast.LENGTH_LONG).show()
//                return false
//            }
//
//            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                Toast.makeText(application, R.string.load_success, Toast.LENGTH_LONG).show()
//                return false
//            }
//        })

//        iv_3.loadBlurImage(this, url4)
//        iv_4.loadCircleImage(this, url4)
//        iv_5.loadRoundCornerImage(this, url4)
//        iv_6.loadGrayImage(this, url4)
//        iv_7.loadResizeXYImage(this, url2, 800, 200)
//        iv_8.loadImageWithTransformation(this, url2, GrayscaleTransformation(), RoundedCornersTransformation(50, 0))
//        iv_9.loadCircleWithBorderImage(this, url2)
//        iv_10.loadImageWithTransformation(this, url2, BlurTransformation(this, 20), GrayscaleTransformation(), CircleCrop())
//        iv_11.loadImage(this, R.drawable.ic_launcher_background)
//        iv_12.loadImage(this, "")
//        iv_13.loadBorderImage(this, url2)

    }

    private fun FrescoTest(){
        val startTime = Date().time
        val simpleDrawView= findViewById(R.id.iv_14) as? SimpleDraweeView
//        simpleDrawView?.setImageURI(url3)  非动图

        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(url3)//设置资源地址
                .setAutoPlayAnimations(true)//设置是否自动播放
                .build()

        simpleDrawView?.controller = controller  //动图为什么动不了？
        val endTime = Date().time
        val totalHeapSize = Runtime.getRuntime().totalMemory()
        val freeHeapSize = Runtime.getRuntime().freeMemory()
        val heapSize = totalHeapSize - freeHeapSize
        Log.e("lilyz_fresco",(endTime - startTime).toString()+"  java_heap:"+heapSize/10000f)
    }

    private fun glideTest(){
        val startTime = Date().time
        Glide.with(this).load(url3).into(iv_1)
        val endTime = Date().time
        val totalHeapSize = Runtime.getRuntime().totalMemory()
        val freeHeapSize = Runtime.getRuntime().freeMemory()
        val heapSize = totalHeapSize - freeHeapSize
        Log.e("lilyz_glide",(endTime - startTime).toString()+"  java_heap:"+heapSize/10000f)
    }

    private fun picassoTest(){
        val startTime = Date().time
        Picasso.with(this)
                .load(url3)
                .into(iv_2)
        val endTime = Date().time
        val totalHeapSize = Runtime.getRuntime().totalMemory()
        val freeHeapSize = Runtime.getRuntime().freeMemory()
        val heapSize = totalHeapSize - freeHeapSize
        Log.e("lilyz_picasso",(endTime - startTime).toString()+"  java_heap:"+heapSize/10000f)

    }

    private fun lilydeTest(){
        circleProgressView.visibility = View.VISIBLE
        val startTime = Date().time
//        iv_0.loadImage(this, url3,onProgressListener = object : OnProgressListener {
//            override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) {
//                // 跟踪进度
//                if (isComplete) {
//                    //网络太快看不见进度条，先设置看见
//                    circleProgressView.visibility = View.VISIBLE
//                }
//                circleProgressView.progress = percentage
//            }
//        })
        iv_0.clearLoadImage(this,url3)
        val endTime = Date().time
        val totalHeapSize = Runtime.getRuntime().totalMemory()
        val freeHeapSize = Runtime.getRuntime().freeMemory()
        val heapSize = totalHeapSize - freeHeapSize
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE)

        Log.e("lilyz_lilyde",(endTime - startTime).toString()+"  java_heap:"+heapSize/10000f)
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    @AfterPermissionGranted(WRITE_EXTERNAL_PERM)
    private fun downloadImage() {
        if (hasStoragePermission()) {
            downloadImageToGallery(iv_1.context, url3)
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.need_write_external),
                    WRITE_EXTERNAL_PERM,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    companion object {
        private const val WRITE_EXTERNAL_PERM = 123
    }

    //想看一下从内存加载是什么情况
    fun loadFromMemory(){
        
    }

}