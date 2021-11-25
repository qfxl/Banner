/*
 *
 *  * Copyright (C)  XU YONGHONG, Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

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
 * desc   : Banner ViewHolder for lambda
 * version: 1.0
 */

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
     * setImageResource
     *
     * @param viewId
     * @param resId
     * @return
     */
    fun setImageResource(@IdRes viewId: Int, @DrawableRes resId: Int): BannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageResource(resId)
        }
        return this
    }

    /**
     * setImageBitmap
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap): BannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageBitmap(bitmap)
        }
        return this
    }

    /**
     * setImageDrawable
     *
     * @param viewId
     * @param drawable
     * @return
     */
    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable): BannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            setImageDrawable(drawable)
        }
        return this
    }
    /**
     * setImageViewAlpha
     *
     * @param viewId
     * @param alpha
     * @return
     */
    fun setImageAlpha(@IdRes viewId: Int, alpha: Int): BannerViewHolder {
        getView<ImageView>(viewId)?.apply {
            val animation = AlphaAnimation(alpha.toFloat(), alpha.toFloat())
            animation.duration = 0
            animation.fillAfter = true
            startAnimation(animation)
        }
        return this
    }

    /**
     * setBackground
     *
     * @param viewId
     * @param color
     * @return
     */
    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BannerViewHolder {
        getView<View>(viewId)?.apply {
            setBackgroundColor(color)
        }
        return this
    }

    /**
     * setOnClickListener
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    fun setOnClickListener(
        @IdRes viewId: Int,
        onClickListener: View.OnClickListener
    ): BannerViewHolder {
        getView<View>(viewId)?.apply {
            setOnClickListener(onClickListener)
        }
        return this
    }

    /**
     * setOnLongClickListener
     *
     * @param viewId
     * @param onLongClickListener
     * @return
     */
    fun setOnLongClickListener(
        @IdRes viewId: Int,
        onLongClickListener: View.OnLongClickListener
    ): BannerViewHolder {
        getView<View>(viewId)?.apply {
            setOnLongClickListener(onLongClickListener)
        }
        return this
    }

    companion object {
        operator fun get(parent: ViewGroup, layoutId: Int): BannerViewHolder {
            val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return BannerViewHolder(convertView)
        }
    }
}