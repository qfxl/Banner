# Banner

## Introduction
![license][1]

Quickly implement the Banner function on Android. The implementation principle is Viewpager2. In addition, it realizes the cycle of ViewPager2, whether it can be manually swiped, the rate of sliding, and provides custom indicators, animations, and quick integration.

## demo



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
|banner_marginStart|dimension|8dp|indicator margin start|
|banner_marginTop|dimension|8dp|indicator margin top|
|banner_marginEnd|dimension|8dp|indicator margin end|
|banner_marginBottom|dimension|8dp|indicator margin bottom|

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
    implementation 'com.github.qfxl:Banner:1.0.0'
}
```

or depend on local lib
```groovy
implementation project(':banner')
```

### 2，xml
```xml
    <com.github.banner.Banner
        android:layout_width="248dp"
        android:layout_height="140dp"
        app:banner_indicatorSelectColor="@color/colorAccent" />
```

or custom attribute

```xml

```
### set source 
```java

```


## Indicators

Banner support following indicators

* CircleIndicator
  
* CircleSmoothIndicator
  
* RectIndicator
  
* RectSmoothIndicator
  
* RoundRectIndicator
  
* RoundRectSmoothIndicator


### 如何自定义指示器
以[PageIndicatorView][6]为例，如果想使用该库作为指示器，可以参考以下代码。
**说明：**[BaseIndicator][7]是继承自LinearLayout，需要做的操作是将指自定义的Indicator通过`addView()`添加进去即可，Banner会在`setAdapter()`之后自动调用`createIndicators()`方法中与内部的ViewPager关联。

```java

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
