# XViewPager

## 简介

XViewPager封装了ViewPager提供了多页面切换的统一解决方案

==|假装有效果图==|

## 主要功能

* 支持自动轮播(view.postRunnable实现)
* 支持循环轮播
* 支持设置滑动速率
* 支持设置是否可以手动滑动
* 支持设置指示器的位置以及禁止使用指示器
* 支持设置指示器及自定义指示器(默认指示器可以设置任意属性)
* 其他所有ViewPager的特性
* 内置3种切换动画

## 设计思路

XViewPager继承自RelativeLayout，将ViewPager和indicator置于其中，同时XViewPager提供了一些ViewPager常用方法的代理，这样在日常使用上保持和ViewPager无差异，如果需要调用ViewPager的所有方法，可通过getViewPager()方法拿到真正的ViewPager进行操作.

## 使用方法

### xml

```xml
 <com.qfxl.view.viewpager.XViewPager
        android:id="@+id/xvp_banner"
        android:layout_width="match_parent"
        android:layout_height="140dp" />
```
或者
```xml
<com.qfxl.view.viewpager.XViewPager
        android:id="@+id/xvp_banner"
        android:layout_width="match_parent"
        android:layout_height="140dp"
        app:XViewPager_autoLoop="true"
        app:XViewPager_default_indicator_gravity="right"
        app:XViewPager_default_indicator_margin="3dp"
        app:XViewPager_default_indicator_normal_resId="@drawable/shape_default_indicator_normal"
        app:XViewPager_default_indicator_select_resId="@drawable/shape_default_indicator_select"
        app:XViewPager_default_indicator_size="8dp"
        app:XViewPager_enableInfinityLoop="true"
        app:XViewPager_indicator_background_color="@android:color/transparent"
        app:XViewPager_indicator_position="bottom"
        app:XViewPager_loopInterval="3000"
        app:XViewPager_page_margin="10dp"
        app:XViewPager_page_offscreen_limit="1"
        app:XViewPager_scrollDuration="500"
        app:XViewPager_touchEnable="true" />
```

### java

```java
XViewPager bannerViewPager = (XViewPager)findViewById(R.id.xvp_banner);
bannerViewPager.setAdapter(mBannerAdapter);
```
或者
```java
 bannerViewPager.setAutoLoop(true)
                .setEnableInfinityLoop(true)
                .setScrollDuration(500)
                .setLoopInterval(2000)
                .setTouchScrollable(true)
                .setIndicatorLayoutBackgroundColor(Color.TRANSPARENT)
                .setIndicatorPosition(XViewPager.IndicatorPosition.BOTTOM)
                .setOffscreenPageLimit(1)
                .setPageMargin(10)
                .setUseDefaultIndicator(true)
                .setDefaultIndicatorGravity(Gravity.CENTER)
                .setDefaultIndicatorNormalResId(R.drawable.shape_default_indicator_normal)
                .setDefaultIndicatorSelectResId(R.drawable.shape_default_indicator_select)
                .setDefaultIndicatorSize(20)
                .setDefaultIndicatorMargin(10)
                .setAdapter(mBannerAdapter);
```

## 自定义属性

|属性|属性说明|类型|默认值|
|:--:|:--:|:--:|:--:|
|XViewPager_enableInfinityLoop|是否无限循环|boolean|true|
|XViewPager_autoLoop|是否自动轮播|boolean|false|
|XViewPager_loopInterval|自动轮播间隔|integer|3000|
|XViewPager_touchEnable|是否允许手动滑动|boolean|true|
|XViewPager_scrollDuration|滑动的速率|integer|800|
|XViewPager_pageMargin|item之间的间距|integer|0|
|XViewPager_pageOffscreenLimit|离屏item个数|integer|1|
|XViewPager_indicatorBackgroundColor|指示器背景色|color|默认底色|
|XViewPager_indicatorPosition|指示器的位置(top,center,bottom)|enum|bottom|
|XViewPager_defaultIndicatorSelectResId|默认指示器选中的资源id|reference|R.drawable.shape_default_indicator_select|
|XViewPager_defaultIndicatorNormalResId|默认指示器正常资源id|reference|R.drawable.shape_default_indicator_normal|
|XViewPager_defaultIndicatorMargin|默认指示器之间的间距|dimension|2dp|
|XViewPager_defaultIndicatorSize|默认指示器的大小|dimension|6dp|
|XViewPager_defaultIndicatorGravity|默认指示器的位置(left,center,right)|enum|center|

## 如何自定义指示器

indicator的容器是一个LinearLayout，所以你要做的操作就是将你的指示器添加到Linearlayout中,sample中有自定义指示器的案例，TextPagerIndicator跟LineIndicator，具体查看sample
```java
public class TextPagerIndicator extends XViewPagerBaseIndicator {

    private TextView textView;

    public TextPagerIndicator(Context context) {
        this(context, null);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.RIGHT);
        setPadding(0, 0, 10, 0);
    }

    @Override
    protected void onItemSelected(int position) {
        textView.setText(position + 1 + "/" + getItemTotalCount());
    }

    @Override
    protected void createIndicators(int itemCount) {
        if (itemCount > 1) {
            textView = new TextView(getContext());
            textView.setTextSize(16);
            textView.setPadding(15, 15, 15, 15);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.shape_text_indicator);
            textView.setText("1/" + itemCount);
            addView(textView);
        }
    }
}
```






