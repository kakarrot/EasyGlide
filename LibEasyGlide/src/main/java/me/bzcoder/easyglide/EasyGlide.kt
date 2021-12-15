package me.bzcoder.easyglide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.util.Preconditions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.bzcoder.easyglide.config.GlideConfigImpl
import me.bzcoder.easyglide.progress.*
import me.bzcoder.easyglide.transformation.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * EasyGlide工具类
 * 更多转换可见 ：https://github.com/wasabeef/glide-transformations
 * @author : BaoZhou
 * @date : 2020/5/9 9:58 AM
 */

object EasyGlide {
    var placeHolderImageView = R.color.transparent
    var circlePlaceholderImageView = R.color.transparent

    /**
     * 加载本地图片
     *
     * @param context
     * @param drawableId
     * @param imageView
     */
    fun ImageView.loadImage(context: Context, @RawRes @DrawableRes drawableId: Int) {
        loadImage(
            context, GlideConfigImpl
                .builder()
                .drawableId(drawableId)
                .isCropCenter(true)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadImage(
        context: Context,
        url: String?,
        @DrawableRes placeHolder: Int = placeHolderImageView,
        onProgressListener: OnProgressListener? = null,
        requestListener: RequestListener<Drawable?>? = null
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .isCropCenter(true)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .progressListener(onProgressListener)
                .requestListener(requestListener)
                .build()
        )
    }

    /**
     * 加载本地图片
     * @param context
     * @param drawableId
     * @param resizeX
     * @param resizeY
     * @param imageView
     */
    @JvmOverloads
    fun ImageView.loadResizeXYImage(
        context: Context,
        url: String?,
        resizeX: Int,
        resizeY: Int,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .isCropCenter(true)
                .isCrossFade(true)
                .resize(resizeX, resizeY)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadCircleImage(
        context: Context,
        url: String?,
        @DrawableRes placeHolder: Int = circlePlaceholderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .isCropCircle(true)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadGrayImage(
        context: Context,
        url: String?,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CenterCrop(), GrayscaleTransformation())
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadBlurImage(
        context: Context,
        url: String?,
        radius: Int = 10,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CenterCrop(), BlurTransformation(context, radius))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadRoundCornerImage(
        context: Context,
        url: String?,
        radius: Int = 40,
        margin: Int = 0,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CenterCrop(), RoundedCornersTransformation(radius, margin))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadCircleWithBorderImage(
        context: Context,
        url: String?,
        borderWidth: Int = 2,
        @ColorInt borderColor: Int = 0xACACAC,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(CircleWithBorderTransformation(borderWidth, borderColor))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    @JvmOverloads
    fun ImageView.loadBorderImage(
        context: Context,
        url: String?,
        borderWidth: Int = 2,
        @ColorInt borderColor: Int = 0xACACAC,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(BorderTransformation(borderWidth, borderColor))
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }

    /**
     * 提供了一下如下变形类，支持叠加使用
     * BlurTransformation
     * GrayScaleTransformation
     * RoundedCornersTransformation
     * CircleCrop
     * CenterCrop
     */
    fun ImageView.loadImageWithTransformation(
        context: Context,
        url: String?,
        vararg bitmapTransformations: BitmapTransformation,
        @DrawableRes placeHolder: Int = placeHolderImageView
    ) {
        loadImage(
            context,
            GlideConfigImpl
                .builder()
                .url(url)
                .transformation(*bitmapTransformations)
                .isCrossFade(true)
                .errorPic(placeHolder)
                .placeholder(placeHolder)
                .imageView(this)
                .build()
        )
    }


    /**
     * 预加载
     *
     * @param context
     * @param url
     */
    fun preloadImage(context: Context, url: String?) {
        Glide.with(context).load(url).preload()
    }

    @SuppressLint("CheckResult")
    fun loadImage(context: Context, config: GlideConfigImpl) {
        Preconditions.checkNotNull(context, "Context is required")
        Preconditions.checkNotNull(config, "ImageConfigImpl is required")
        Preconditions.checkNotNull(config.imageView, "ImageView is required")
        val glideRequest = if (config.drawableId != 0) {
            EasyGlideApp.with(context).load(config.drawableId)
        } else {
            EasyGlideApp.with(context).load(config.url)
        }
        glideRequest.apply {
            when (config.cacheStrategy) {
                0 -> diskCacheStrategy(DiskCacheStrategy.ALL)
                1 -> diskCacheStrategy(DiskCacheStrategy.NONE)
                2 -> diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                3 -> diskCacheStrategy(DiskCacheStrategy.DATA)
                4 -> diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                else -> diskCacheStrategy(DiskCacheStrategy.ALL)
            }
            if (config.isCrossFade) {
                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                transition(DrawableTransitionOptions.withCrossFade(factory))
            }
            if (config.isCrossFade) {
                val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                transition(DrawableTransitionOptions.withCrossFade(factory))
            }
            if (config.isImageRadius()) {
                transform(RoundedCorners(config.imageRadius))
            }
            if (config.isBlurImage) {
                transform(BlurTransformation(context, config.blurValue))
            }
            //glide用它来改变图形的形状
            if (config.transformation != null) {
                transform(*config.transformation)
            }
            if (config.placeHolderDrawable != null) {
                placeholder(config.placeHolderDrawable)
            }
            //设置占位符
            if (config.placeholder != 0) {
                placeholder(config.placeholder)
            }
            //设置错误的图片
            if (config.errorPic != 0) {
                error(config.errorPic)
            }
            //设置请求 url 为空图片
            if (config.fallback != 0) {
                fallback(config.fallback)
            }
            if (config.resizeX != 0 && config.resizeY != 0) {
                override(config.resizeX, config.resizeY)
            }
            if (config.isCropCenter) {
                centerCrop()
            }
            if (config.isCropCircle) {
                circleCrop()
            }
            if (config.formatType != null) {
                format(config.formatType)
            }
            if (config.isFitCenter) {
                fitCenter()
            }
            if (config.requestListener != null) {
                addListener(config.requestListener)
            }
            into(GlideImageViewTarget(config.imageView, config.url))
        }

        if (config.onProgressListener != null && !config.url.isNullOrBlank()) {
            ProgressManager.addListener(config.url!!, config.onProgressListener)
        }
    }

    /**
     * 清除本地缓存
     */
    suspend fun clearDiskCache(context: Context) {
        withContext(Dispatchers.IO) {
            Glide.get(context).clearDiskCache()
        }
    }

    /**
     * 清除内存缓存
     */
    fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }

    /**
     * 取消图片加载
     */
    fun clearImage(context: Context, imageView: ImageView) {
        EasyGlideApp.get(context).requestManagerRetriever[context].clear(imageView)
    }

    /**
     * 下载图片
     * @param context
     * @param imgUrl
     */
    fun downloadImage(context: Context, imgUrl: String?): File? {
        return try {
            Glide.with(context).download(imgUrl).submit().get(30, TimeUnit.SECONDS)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
