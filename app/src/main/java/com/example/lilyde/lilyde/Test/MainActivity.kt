package com.example.lilyde.lilyde.Test

import android.Manifest
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.lilyde.progress.OnProgressListener
import com.example.lilyde.transformation.BlurTransformation
import com.example.lilyde.transformation.GrayscaleTransformation
import com.example.lilyde.transformation.RoundedCornersTransformation
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(){
    var url1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url2 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url3 = "http://img.mp.itc.cn/upload/20170311/48180d37e4474628900d058f3cc5ee7d_th.gif"
    var url4 = "http://img.mp.itc.cn/upload/20170311/33f2b7f7ffb04ecb81e42405e20b3fdc_th.gif"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        circleProgressView.visibility = View.VISIBLE
        iv_0.loadImage(this, url4,onProgressListener = object : OnProgressListener {
            override fun onProgress(isComplete: Boolean, percentage: Int, bytesRead: Long, totalBytes: Long) {
                // 跟踪进度
                if (isComplete) {
                    circleProgressView.visibility = View.GONE
                }
                circleProgressView.progress = percentage
            }
        })


//        val animator = ValueAnimator.ofInt(0, 100)
//        animator.duration = 2000
//        animator.repeatCount = ValueAnimator.INFINITE
//        animator.interpolator = LinearInterpolator()
//        animator.addUpdateListener { circleProgressView.progress = (animator.animatedValue as Int) }
//        animator.start()
        Lilyde.placeHolderImageView = R.color.red
        Lilyde.circlePlaceholderImageView = R.color.red
        iv_1.setOnClickListener { downloadImage() }
        iv_1.loadImage(this, url3)
        iv_2.loadImage(this, url4, requestListener = object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                Toast.makeText(application, R.string.load_failed, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                Toast.makeText(application, R.string.load_success, Toast.LENGTH_LONG).show()
                return false
            }
        })

        iv_3.loadBlurImage(this, url4)
        iv_4.loadCircleImage(this, url4)
        iv_5.loadRoundCornerImage(this, url4)
        iv_6.loadGrayImage(this, url4)
        iv_7.loadResizeXYImage(this, url2, 800, 200)
        iv_8.loadImageWithTransformation(this, url2, GrayscaleTransformation(), RoundedCornersTransformation(50, 0))
        iv_9.loadCircleWithBorderImage(this, url2)
        iv_10.loadImageWithTransformation(this, url2, BlurTransformation(this, 20), GrayscaleTransformation(), CircleCrop())
        iv_11.loadImage(this, R.drawable.ic_launcher_background)
        iv_12.loadImage(this, "")
        iv_13.loadBorderImage(this, url2)
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

}