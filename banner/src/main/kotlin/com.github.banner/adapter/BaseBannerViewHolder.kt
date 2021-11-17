package com.github.banner.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

class BaseBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> getView(@IdRes id: Int): T? {
        var v: View? = mViews.get(id)
        if (v == null) {
            v = itemView.findViewById(id)
            mViews.put(id, v)
        }
        return v as T?
    }

    /**
     * 设置ImageView的资源id
     *
     * @param viewId
     * @param resId
     * @return
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): BaseBannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageResource(resId)
        }
        return this
    }

    /**
     * 设置ImageView的Bitmap
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap): BaseBannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageBitmap(bitmap)
        }
        return this
    }

    /**
     * 设置ImageView的Drawable
     *
     * @param viewId
     * @param drawable
     * @return
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable): BaseBannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageDrawable(drawable)
        }
        return this
    }

    /**
     * 设置ImageView的url
     *
     * @param viewId
     * @param url
     * @return
     */
    fun setImageUrl(@IdRes viewId: Int, url: String): BaseBannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            //TODO
        }
        return this
    }

    /**
     * 设置ImageView的alpha
     *
     * @param viewId
     * @param alpha
     * @return
     */
    fun setImageAlpha(@IdRes viewId: Int, alpha: Int): BaseBannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            val animation = AlphaAnimation(alpha.toFloat(), alpha.toFloat())
            animation.duration = 0
            animation.fillAfter = true
            startAnimation(animation)
        }
        return this
    }

    /**
     * 设置背景色
     *
     * @param viewId
     * @param color
     * @return
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            setBackgroundColor(color)
        }
        return this
    }

    /**
     * 设置View的可见
     *
     * @param viewId
     * @param visible
     * @return
     */
    fun setVisibility(@IdRes viewId: Int, visible: Boolean): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            visibility = if (visible) View.VISIBLE else View.INVISIBLE
        }
        return this
    }

    /**
     * 设置View的可见
     *
     * @param viewId
     * @param visible
     * @return
     */
    fun setGone(@IdRes viewId: Int, visible: Boolean): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            visibility = if (visible) View.VISIBLE else View.GONE
        }
        return this
    }

    /**
     * 给View设置tag
     *
     * @param viewId
     * @param newTag
     * @return
     */
    fun setTag(@IdRes viewId: Int, newTag: Any): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            tag = newTag
        }
        return this
    }

    /**
     * 给View设置tag
     *
     * @param viewId
     * @param tagKey
     * @param tagValue
     * @return
     */
    fun setTag(@IdRes viewId: Int, tagKey: Int, tagValue: Any): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            setTag(tagKey, tagValue)
        }
        return this
    }

    /**
     * 设置某个View的点击事件
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    fun setOnClickListener(
        @IdRes viewId: Int,
        onClickListener: View.OnClickListener
    ): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            setOnClickListener(onClickListener)
        }
        return this
    }

    /**
     * 设置某个View的长按事件
     *
     * @param viewId
     * @param onLongClickListener
     * @return
     */
    fun setOnLongClickListener(
        @IdRes viewId: Int,
        onLongClickListener: View.OnLongClickListener
    ): BaseBannerViewHolder {
        getView<View>(viewId)?.apply {
            setOnLongClickListener(onLongClickListener)
        }
        return this
    }

    companion object {
        operator fun get(parent: ViewGroup, layoutId: Int): BaseBannerViewHolder {
            val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return BaseBannerViewHolder(convertView)
        }
    }
}