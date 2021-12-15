package me.bzcoder.sample

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.bzcoder.easyglide.EasyGlide
import me.bzcoder.easyglide.EasyGlide.loadBlurImage
import me.bzcoder.easyglide.EasyGlide.loadBorderImage
import me.bzcoder.easyglide.EasyGlide.loadCircleImage
import me.bzcoder.easyglide.EasyGlide.loadCircleWithBorderImage
import me.bzcoder.easyglide.EasyGlide.loadGrayImage

import me.bzcoder.easyglide.EasyGlide.loadImage
import me.bzcoder.easyglide.EasyGlide.loadImageWithTransformation
import me.bzcoder.easyglide.EasyGlide.loadResizeXYImage
import me.bzcoder.easyglide.EasyGlide.loadRoundCornerImage
import me.bzcoder.easyglide.progress.CircleProgressView
import me.bzcoder.easyglide.progress.OnProgressListener
import me.bzcoder.easyglide.sample.R
import me.bzcoder.easyglide.transformation.BlurTransformation
import me.bzcoder.easyglide.transformation.GrayscaleTransformation
import me.bzcoder.easyglide.transformation.RoundedCornersTransformation

/**
 * Kotlin Sample功能
 * @author : BaoZhou
 * @date : 2020/5/9 3:13 PM
 */
class MainActivity : AppCompatActivity() {
    var url1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url2 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=579539400,2248223712&fm=26&gp=0.jpg"
    var url3 = "http://img.mp.itc.cn/upload/20170311/48180d37e4474628900d058f3cc5ee7d_th.gif"
    var url4 = "http://img.mp.itc.cn/upload/20170311/33f2b7f7ffb04ecb81e42405e20b3fdc_th.gif"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private val circleProgressView: CircleProgressView by lazy {
        findViewById(R.id.circleProgressView)
    }

    private val iv_0: ImageView by lazy {
        findViewById(R.id.iv_0)
    }

    private val iv_1: ImageView by lazy {
        findViewById(R.id.iv_1)
    }

    private val iv_2: ImageView by lazy {
        findViewById(R.id.iv_2)
    }

    private val iv_3 by lazy {
        findViewById<ImageView>(R.id.iv_3)
    }
    private val iv_4 by lazy {
        findViewById<ImageView>(R.id.iv_4)
    }
    private val iv_5 by lazy {
        findViewById<ImageView>(R.id.iv_5)
    }
    private val iv_6 by lazy {
        findViewById<ImageView>(R.id.iv_6)
    }
    private val iv_7 by lazy {
        findViewById<ImageView>(R.id.iv_7)
    }
    private val iv_8 by lazy {
        findViewById<ImageView>(R.id.iv_8)
    }
    private val iv_9 by lazy {
        findViewById<ImageView>(R.id.iv_9)
    }
    private val iv_10 by lazy {
        findViewById<ImageView>(R.id.iv_10)
    }
    private val iv_11 by lazy {
        findViewById<ImageView>(R.id.iv_11)
    }
    private val iv_12 by lazy {
        findViewById<ImageView>(R.id.iv_12)
    }
    private val iv_13 by lazy {
        findViewById<ImageView>(R.id.iv_13)
    }

    private fun initView() {
        circleProgressView.visibility = View.VISIBLE
        iv_0.loadImage(this, url4,onProgressListener = object :OnProgressListener{
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
        EasyGlide.placeHolderImageView = R.color.red
        EasyGlide.circlePlaceholderImageView = R.color.red
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
        iv_11.loadImage(this, R.drawable.test)
        iv_12.loadImage(this, "")
        iv_13.loadBorderImage(this, url2)
    }

    private fun hasStoragePermission() = true

    private fun downloadImage() {
        //downloadImage(iv_1.context, url3)
    }

    companion object {
        private const val WRITE_EXTERNAL_PERM = 123
    }
}