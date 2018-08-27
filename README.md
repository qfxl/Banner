# Banner

## 简介
![license][1]

快速实现Android上的Banner功能，实现原理是Viewpager，此外实现了ViewPager的循环轮播，是否可以手动滑动，滑动的速率，提供自定义指示器，动画，快速集成。

## 效果图
![此处输入图片的描述][5]

![此处输入图片的描述][2]

![此处输入图片的描述][3]

![此处输入图片的描述][4]


## Attributes属性

|attr|format|default|描述|
|:-:|:-:|:-:|:-|
|banner_infinityLoop|boolean|true|是否支持循环|
|banner_autoLoop|boolean|true|是否自动轮播|
|banner_loopInterval|integer|3000|轮播间隔|
|banner_touchEnable|boolean|true|是否可以手动滑动|
|banner_scrollDuration|integer|600|滑动速率|
|banner_pageMargin|dimension|0|内部ViewPager的pageMargin|
|banner_pageOffscreenLimit|integer|1|内部ViewPager加载离屏数|
|banner_isDefaultIndicator|boolean|true|是否采用默认指示器|
|banner_indicatorBackgroundColor|color|-|指示器背景色|
|banner_indicatorPosition|enum|bottom|指示器位置top,center,bottom|
|banner_indicatorWidth|dimension|MATCH_PARENT|指示器宽|
|banner_indicatorHeight|dimension|wrap_content|指示器高|
|banner_indicatorSelectResId|reference|默认提供的指示器|指示器选中的资源|
|banner_indicatorNormalResId|reference|默认提供的指示器|指示器未选中的资源|
|banner_indicatorItemMargin|dimension|2dp|默认指示器item的间距|
|banner_indicatorItemWidth|dimension|6dp|默认指示器item宽度|
|banner_indicatorItemHeight|dimension|6dp|默认指示器item高度|
|banner_indicatorGravity|enum|center|默认值指示器内部的gravity(left,center,right)|

属性都有对应的java api。

## 使用步骤
### 1，Gradle
```groovy
implementation 'com.qfxl:banner:1.0.1-release'
```
或者使用本地lib
```groovy
implementation project(':banner')
```
### 2，xml声明Banner
```xml
    <com.qfxl.view.banner.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="180dp" />
```
或者使用自定义属性
```xml
<com.qfxl.view.banner.Banner
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        app:banner_autoLoop="true"
        app:banner_indicatorBackgroundColor="@android:color/transparent"
        app:banner_indicatorGravity="center"
        app:banner_indicatorHeight="16dp"
        app:banner_indicatorItemHeight="2dp"
        app:banner_indicatorItemWidth="10dp"
        app:banner_indicatorItemMargin="3dp"
        app:banner_indicatorNormalResId="@drawable/sp_line_indicator_normal"
        app:banner_indicatorPosition="bottom"
        app:banner_indicatorSelectResId="@drawable/sp_line_indicator_selected"
        app:banner_infinityLoop="true"
        app:banner_isDefaultIndicator="true"
        app:banner_loopInterval="1000"
        app:banner_pageMargin="0dp"
        app:banner_pageOffscreenLimit="3"
        app:banner_scrollDuration="600"
        app:banner_touchEnable="true" />
```
## 3，实现图片加载器（如果使用自定义适配器则可以不用实现）
```java
private IBannerImageLoader imageLoader = new IBannerImageLoader() {
        @Override
        public void displayImage(Context context, ImageView imageView, Object path) {
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.img_placeholder)).load(path).into(imageView);
        }

        @Override
        public ImageView createImageViewView(Context context) {
            return null ;
        }
    };
```
## 设置数据源
```java
banner.autoReady(urls, imageLoader, new BannerDefaultAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position, Object resource) {

            }
        });
```
或者使用代码进行详细配置
```java
 banner.setAutoLoop(true)
                .setInfinityLoop(true)
                .setLoopInterval(1000)
                .setTouchScrollable(true)
                .setIsDefaultIndicator(true)
                .setIndicatorLayoutBackgroundColor(Color.TRANSPARENT)
                .setIndicatorPosition(Banner.IndicatorPosition.BOTTOM)
                .setIndicatorGravity(Gravity.CENTER)
                .setIndicatorHeight(Utils.dp2px(this, 16))
                .setIndicatorItemWidth(Utils.dp2px(this, 6))
                .setIndicatorItemHeight(Utils.dp2px(this, 6))
                .setIndicatorItemMargin(Utils.dp2px(this, 3))
                .setIndicatorNormalResId(R.drawable.shape_default_indicator_normal)
                .setIndicatorSelectResId(R.drawable.shape_default_indicator_select)
                .setOffscreenPageLimit(3)
                .setScrollDuration(600)
                .setBannerCLickListener(new BannerDefaultAdapter.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object resource) {

                    }
                })
                .setImageResources(urls)
                .setImageLoader(imageLoader)
                .ready();
```

## 关于指示器
Banner内置了一个指示器，提供选中、未选中资源设置，大小设置，背景色设置，间距设置。如果默认指示器不能满足，可以自己实现想要的指示器，或者使用其他开源指示器。

### 如何自定义指示器
以[PageIndicatorView][6]为例，如果想使用该库作为指示器，可以参考以下代码。
**说明：**[BaseIndicator][7]是继承自LinearLayout，需要做的操作是将指自定义的Indicator通过`addView()`添加进去即可，Banner会在`setAdapter()`之后自动调用`createIndicators()`方法中与内部的ViewPager关联。

```java
public class PagerIndicatorViewIndicator extends BaseIndicator {
    private PageIndicatorView pageIndicatorView;
    private AnimationType animationType = AnimationType.DROP;

    public PagerIndicatorViewIndicator(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    public PagerIndicatorViewIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
    }

    public PagerIndicatorViewIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    @Override
    protected void onItemSelected(int position) {
        pageIndicatorView.setSelection(position);
    }

    @Override
    protected void onItemScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pageIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    protected void createIndicators(int itemCount) {
        pageIndicatorView = new PageIndicatorView(getContext());
        int height = animationType == AnimationType.DROP? Utils.dp2px(getContext(), 16) : Utils.dp2px(getContext(), 10);
        pageIndicatorView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, height));
        pageIndicatorView.setDynamicCount(true);
        pageIndicatorView.setSelected(Color.RED);
        pageIndicatorView.setUnselectedColor(Color.GRAY);
        pageIndicatorView.setInteractiveAnimation(true);
        pageIndicatorView.setRadius(3);
        pageIndicatorView.setAnimationType(animationType);
        addView(pageIndicatorView);
    }
}
```

## 关于切换动画
`Banner`提供了`setPageTransformer()`方法，该方法调用了内部的`ViewPager#setPageTransformer()`方法。
`Banner`提供内置动画有：

* CardPageTransform
* CubeOutPageTransform
* DepthPageTransform
* RotateDownPageTransform
* ZoomOutPageTransformer

## Thanks
[PageIndicatorView][8]

> 具体的详细功能可以查看Sample。


  [1]: https://camo.githubusercontent.com/fc8e0c80ec74887c0cbc124b5e8cec1009e6f596/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6c6963656e73652d417061636865253230322e302d626c75652e7376673f7374796c653d666c6174
  [2]: https://github.com/qfxl/Banner/blob/master/pic/sample_1.png
  [3]: https://github.com/qfxl/Banner/blob/master/pic/sample_2.png
  [4]: https://github.com/qfxl/Banner/blob/master/pic/sample_3.png
  [5]: https://github.com/qfxl/Banner/blob/master/pic/sample.gif
  [6]: https://github.com/romandanylyk/PageIndicatorView
  [7]: https://github.com/qfxl/Banner/blob/master/banner/src/main/java/com/qfxl/view/indicator/BaseIndicator.java
  [8]: https://github.com/romandanylyk/PageIndicatorView
