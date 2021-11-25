# Banner

## Introduction
![license][1]

Quickly implement the Banner function on Android. The implementation principle is Viewpager2. In addition, It implements the infinite loop of ViewPager2, whether it can be manually swiped, the rate of scrolling, and provides custom indicators, animations, quick integration.

## demo

![demo_basic](https://github.com/qfxl/Banner/blob/master/imgs/basic_demo.gif)

## Attributes

|attr|format|default|description|
|:-:|:-:|:-:|:-|
|banner_orientation|enum|0|banner orientation,horizontal(0),vertical(1)|
|banner_enableInfinityLoop|boolean|true|enable infinity loop or not|
|banner_enableAutoScroll|boolean|true|enable carousel auto scroll or not|
|banner_autoScrollWhenAttached|boolean|true|automatically turn on carousel when binding data|
|banner_autoScrollDuration|integer|3000|auto scroll duration|
|banner_userInputEnable|boolean|true|enable user input or not|
|banner_pageMargin|dimension|0|banner item page margin|
|banner_pageOffscreenLimit|integer|-1|banner page offscreenLimit, default is ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT|
|banner_indicatorStyle|enum|0|NONE(0),CIRCLE(1),RECT(2),ROUND_RECT(3),CIRCLE_SMOOTH(4),RECT_SMOOTH(5),ROUND_RECT_SMOOTH(6)|
|banner_indicatorDefaultColor|color|Color.WHITE|banner indicator default color|
|banner_indicatorSelectColor|color|Theme.primary|banner indicator select color|
|banner_indicatorItemSpace|dimension|4dp|banner indicator each item space|
|banner_indicatorItemWidth|dimension|CircleIndicator(6dp),RectIndicator(10dp),RoundRectIndicator(10dp)|Banner indicator item width|
|banner_indicatorItemSelectWidth|dimension|CircleIndicator(6dp),RectIndicator(10dp),RoundRectIndicator(10dp)|Banner indicator item select width|
|banner_indicatorItemHeight|dimension|CircleIndicator(6dp),RectIndicator(2dp),RoundRectIndicator(2dp)|Banner indicator item height|
|banner_indicatorItemSelectHeight|dimension|CircleIndicator(6dp),RectIndicator(2dp),RoundRectIndicator(2dp)|Banner indicator item select height|
|banner_indicatorRoundRectRadius|dimension|RoundRectIndicator#width/2|RoundRectIndicator item radius|
|banner_indicatorCenterInParent|boolean|false|indicator position center in Banner or not|
|banner_indicatorAlignParentTop|boolean|false|indicator position align Banner top or not|
|banner_indicatorAlignParentEnd|boolean|false|indicator position align Banner end or not|
|banner_indicatorAlignParentBottom|boolean|false|indicator position align Banner bottom or not|
|banner_indicatorCenterHorizontal|boolean|false|indicator position center horizontal in banner or not|
|banner_indicatorCenterVertical|boolean|false|indicator position center vertical in Banner or not |
|banner_indicatorMarginStart|dimension|8dp|indicator margin start|
|banner_indicatorMarginTop|dimension|8dp|indicator margin top|
|banner_indicatorMarginEnd|dimension|8dp|indicator margin end|
|banner_indicatorMarginBottom|dimension|8dp|indicator margin bottom|

> each attribute can be set dynamically by Kotlin code

## How to use
### 1，Gradle
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
     // only support AndroidX
}
```

or depend on local lib
```groovy
implementation project(':banner')
```

### 2，xml
```xml
    <com.github.banner.Banner
        android:layout_width="match_parent"
        android:layout_height="140dp"/>
```

or custom attribute

```xml
  <com.github.banner.Banner
        android:layout_width="match_parent"
        android:layout_height="140dp"
        android:layout_marginTop="16dp"
        app:banner_autoScrollDuration="700"
        app:banner_autoScrollInterval="2000"
        app:banner_autoScrollWhenAttached="true"
        app:banner_enableAutoScroll="true"
        app:banner_enableInfinityLoop="true"
        app:banner_indicatorAlignParentBottom="true"
        app:banner_indicatorCenterHorizontal="true"
        app:banner_indicatorDefaultColor="#999"
        app:banner_indicatorItemHeight="8dp"
        app:banner_indicatorItemSelectHeight="10dp"
        app:banner_indicatorItemSelectWidth="10dp"
        app:banner_indicatorItemSpace="6dp"
        app:banner_indicatorItemWidth="8dp"
        app:banner_indicatorMarginBottom="16dp"
        app:banner_indicatorSelectColor="@color/colorAccent"
        app:banner_indicatorStyle="CIRCLE"
        app:banner_orientation="horizontal"
        app:banner_pageMargin="12dp"
        app:banner_pageOffscreenLimit="3"
        app:banner_userInputEnable="true" />
```
### set source
```java
fun <T> render(
    @LayoutRes pageLayoutId: Int,
    dataList: List<T>,
    renderScope: BannerViewHolder.(Int, T) -> Unit
) {
    setAdapter(BannerAdapter<T>(pageLayoutId){ position, t ->
        renderScope(position, t)
    }.apply {
        submitList(dataList)
    })
}    
```

or just invoke `Banner#setAdapter(RecyclerView.Adapter<BannerViewHolder>)`


for example, use glide load url to imageView:

```java
mBanner.render(
    R.layout.banner_item_image,
    listOf(
        "url",
        "url",
        "url"
    )
) { position, t ->
    Glide.with(itemView).load(t).into(itemView as ImageView)
}
```

## Indicators

`Banner` support following indicators

* CircleIndicator

![indicator_circle](https://github.com/qfxl/Banner/blob/master/imgs/indicator_circle.gif)

* CircleSmoothIndicator

![indicator_circle_smooth](https://github.com/qfxl/Banner/blob/master/imgs/indicator_circle_smooth.gif)

* RectIndicator

![indicator_rect](https://github.com/qfxl/Banner/blob/master/imgs/indicator_rect.gif)

* RectSmoothIndicator

![indicator_rect_smooth](https://github.com/qfxl/Banner/blob/master/imgs/indicator_rect_smooth.gif)

* RoundRectIndicator

![indicator_round_rect](https://github.com/qfxl/Banner/blob/master/imgs/indicator_round_rect.gif)

* RoundRectSmoothIndicator

![indicator_round_rect_smooth](https://github.com/qfxl/Banner/blob/master/imgs/indicator_round_rect_smooth.gif)

### CustomIndicator

What you need to do is to inherit from BaseIndicator, and then implement the onMeasure, drawHorizontal, and drawVertical functions.Call setDefaultValue to set the default value.

for example, demos show how to custome a NumberIndicator.

```java
class NumberIndicator(context: Context) : BaseIndicator(context) {
    private val bgPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#99999999")
        }
    }

    private val defaultText = "0/0"

    private val defaultPadding = 6.dp

    init {
        indicatorPaint.apply {
            color = Color.WHITE
            textSize = 14f.sp
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minWidth = indicatorPaint.measureText(defaultText) + defaultPadding * 2
        val fm = indicatorPaint.fontMetrics
        val minHeight = fm.descent - fm.ascent + defaultPadding * 2

        val measuredSize = max(minWidth, minHeight)
        setMeasuredDimension(measuredSize.toInt(), measuredSize.toInt())
    }

    override fun drawHorizontal(canvas: Canvas?) {
        canvas?.apply {
            drawCircle(width / 2f, height / 2f, width / 2f, bgPaint)
            drawText("${currentPage+1}/$itemCount", width / 2f, height / 2f - (indicatorPaint.descent() + indicatorPaint.ascent()) / 2, indicatorPaint)
        }
    }

    override fun drawVertical(canvas: Canvas?) {
        canvas?.apply {
            drawCircle(width / 2f, height / 2f, width / 2f, bgPaint)
            drawText("${currentPage+1}/$itemCount", width / 2f, height / 2f - (indicatorPaint.descent() + indicatorPaint.ascent()) / 2, indicatorPaint)
        }
    }
}
```

## PageTransformer
`Banner` provides `setPageTransformer()` function, which calls the internal `ViewPager2#setPageTransformer()` function.
also Banner provides built-in animations:

* OverlapSliderTransformer

![overlap](https://github.com/qfxl/Banner/blob/master/imgs/page_overlap.gif)

To achieve this effect, you need to set the revelWidth of `Banner`. For easy operation, `Banner` provides

```java
fun supportPageOverlap(
        minScale: Float = 0.8f,
        unSelectedItemRotation: Float = 0f,
        unSelectedItemAlpha: Float = 1f,
        itemGap: Float = 0f,
        revealWidthOrHeight: Int = 20.dp
    )
```


* MarginPageTransformer

![margin](https://github.com/qfxl/Banner/blob/master/imgs/page_margin.gif)

This effect also needs to set the revelWidth of `Banner`, For easy operation, `Banner` provides

```java
fun supportMultiPage(itemMargin: Int = 10.dp, revealWidthOrHeight: Int = 20.dp)

```

* ScaleInTransformer

![margin_scale](https://github.com/qfxl/Banner/blob/master/imgs/page_margin_scale.gif)

This effect also needs to set the revelWidth of `Banner`, For easy operation, `Banner` provides

```java
 fun supportMultiScalePage(
        minScale: Float = 0.8f,
        itemMargin: Int = 10.dp,
        revealWidthOrHeight: Int = 20.dp
    )
```

* DepthPageTransformer

![depth](https://github.com/qfxl/Banner/blob/master/imgs/page_depth.gif)

* ZoomOutPageTransformer

![zoomout](https://github.com/qfxl/Banner/blob/master/imgs/page_zoomout.gif)


## MultiPage

`Banner` is implemented using `ViewPager2`, it is easy to implement multiPage, which is implemented by the itemViewType classification of `RecyclerView`.

![multiPage](https://github.com/qfxl/Banner/blob/master/imgs/multi_page.gif)

```java
 class MultiPageAdapter : RecyclerView.Adapter<BannerViewHolder>() {

        companion object {
            private const val VIEW_TYPE_IMAGE = 1
            private const val VIEW_TYPE_TEXT = 2
            private const val VIEW_TYPE_BACKGROUND = 3
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> VIEW_TYPE_IMAGE
                1 -> VIEW_TYPE_TEXT
                else -> VIEW_TYPE_BACKGROUND
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            val layoutId = when (viewType) {
                VIEW_TYPE_IMAGE -> {
                    R.layout.banner_item_multi_page_image
                }
                VIEW_TYPE_TEXT -> {
                    R.layout.banner_item_multi_page_text
                }
                else -> {
                    R.layout.banner_item_multi_page_background
                }
            }
            return BannerViewHolder(
                LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            )
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
            when (getItemViewType(position)) {
                VIEW_TYPE_IMAGE -> {
                    (holder.itemView as ImageView).setImageResource(R.drawable.img_banner)
                }
                VIEW_TYPE_TEXT -> {
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                    (holder.itemView as TextView).text = "This is a Text Page"
                }
                else -> {
                    holder.itemView.setBackgroundColor(Color.parseColor("#BEEDC7"))
                }
            }
        }

        override fun getItemCount(): Int {
            return 3
        }
    }
```

attach to `Banner`

```java
mBanner.setAdapter(MultiPageAdapter())
```

[1]: https://camo.githubusercontent.com/fc8e0c80ec74887c0cbc124b5e8cec1009e6f596/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6c6963656e73652d417061636865253230322e302d626c75652e7376673f7374796c653d666c6174
  
 
