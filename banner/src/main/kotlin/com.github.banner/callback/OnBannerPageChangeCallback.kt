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

package com.github.banner.callback

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : callback for banner page changed
 * version: 1.0
 */

interface OnBannerPageChangeCallback {

   /**
    * This method will be invoked when the current page is scrolled,
    * either as part of a programmatically initiated smooth scroll or a user initiated touch scroll.
    * @param position – Position index of the first page currently being displayed.
    * Page position+1 will be visible if positionOffset is nonzero.
    * @param positionOffset – Value from [0, 1) indicating the offset from the page at position.
    *@param positionOffsetPixels – Value in pixels indicating the offset from position
    */
   fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)

   /**
    * This method will be invoked when a new page becomes selected.
    * Animation is not necessarily complete.
    * @param position – Position index of the new selected page.
    */
   fun onPageSelected(position: Int)

   /**
    * Called when the scroll state changes.
    * Useful for discovering when the user begins dragging,
    * when a fake drag is started, when the pager is automatically settling to the current page,
    * or when it is fully stopped/idle.
    * state can be one of SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or SCROLL_STATE_SETTLING.
    */
   fun onPageScrollStateChanged(state: Int)
}