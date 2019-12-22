# Android-UIX：Android UI 解决方案

![SLicense](https://img.shields.io/hexpm/l/plug.svg)
![Version](https://img.shields.io/maven-metadata/v/https/dl.bintray.com/easymark/Android/me/shouheng/ui/uix-core/maven-metadata.xml.svg)

## 1、关于项目

是否厌倦了每次启动一个项目的时候要复制粘贴大量的 UI 页面和控件的代码，或者想要进行快速开发却寻找不到一套可用的 UI 框架？那么你应该尝试一下这个库 Android-uix。

作为整个 MVVM 架构的一部分，我们开发这个库的目的就在于提供一套通用的 UI 控件和 UI 页面。比如，关于页面、用户信息设置页面、通用对话框等。

## 2、在项目中引用

在项目中接入我们的库是非常简单的。首先，在项目的 Gradle 中加入 jcenter 仓库：

```gradle
repositories {
    jcenter()
}
```

然后在你的项目依赖中直接引用我们的库即可：

```gradle
implementation 'me.shouheng.ui:uix-core:latest-version'
```

然后你需要在自定义 Application 中初始化类库。这里主要的目的也是初始化一个全局的 Context，所以不用担心这里会因为耗时太长而影响用户体验：

```kotlin
class SampleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        UIX.init(this)
    }
}
```

## 3、在项目中使用 Android UIX

根据项目目前所包含的 UI 控件，功能如下：

首先，对于 RecyclerView 的 Adapter 封装，我们直接引用了 [BRBAH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，因为正如它声称的那样，确实可以为我们节省 60% 到 70% 的代码。而且项目中的其他的页面或控件的开发也是基于该库来完成的。

### 3.1 对话框

在我们的项目中没有使用 Android 原生的对话框，而是自定义了一套对话框。对话框整体基于构建者模式进行设计，为你提供了强大的可自定义的功能。比如，从上、中间或者下面弹出，还可以自定义夜间主题和对话框边距等。

在我们的对话框的设计中，将对话框分成了标题、内容和底部三个部分。你需要分别实现 IDialogTitle IDialogContent 和 IDialogFooter 三个接口。当然，我们还为您提供了一些默认的实现。比如，简单的内容展示、文本编辑、地址选择和列表对话框等等。不论从自定义还是默认提供的功能，它都足够强大。

下面是一个对话框的一般的用例：

```kotlin
BeautyDialog.Builder()
        .setDialogStyle(STYLE_WRAP)
        .setDialogTitle(SimpleTitle.Builder()
                .setTitle("Title (red/18)")
                .setTitleSize(18f)
                .setTitleColor(Color.RED)
                .build())
        .setDialogContent(SampleContent())
        .setDialogBottom(SimpleFooter.Builder()
                .setBottomStyle(BUTTON_RIGHT_ONLY)
                .setRightText("OK")
                .setRightTextColor(Color.RED)
                .setOnClickListener(object : SimpleFooter.OnClickListener {
                    override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                        dialog.dismiss()
                    }
                }).build())
        .build().show(supportFragmentManager, "normal")
```

### 3.2 图片选择框架

对于图片选择，我们直接基于知乎开源的 [Matisse](https://github.com/Shouheng88/Matisse) 进行了封装，在其原有的功能之上提供了夜间和白天两种主题。

### 3.3 图片浏览页面

### 3.4 网页浏览页面

基于 [AgentWeb](https://github.com/Justson/AgentWeb) 做了简单的封装。

### 3.5 关于页面

### 3.6 RecyclerView

- RV 装饰器
- RV 为空的页面的控件封装
- RV 适配器
- 数据源监听的 RV 控件

### 其他必选的控件库

- 圆形图片
- Switch 按钮
- Glide 图片圆角变换
- 正方形的 FrameLayout 和 LinearLayout 布局
- 防止连续点击的 NoDoubleClickListener
- ViewPager 的指示器
- 倒计时控件
- 具备清空功能的文本输入框
- 可以查看输入内容的密码输入框
- 垂直的文字展示控件
- 正则表达式校验的输入框

## 4、更新日志

- 1.1.4
    - 设置列表页增加说明性质的文字，可以自定义背景等
    - Crash 崩溃捕捉页面
- 1.1.3 稳定版本发布

## 5、关于作者

你可以通过访问下面的链接来获取作者的信息：

1. Github: https://github.com/Shouheng88
2. 掘金：https://juejin.im/user/585555e11b69e6006c907a2a
3. CSDN：https://blog.csdn.net/github_35186068

## License

```
Copyright (c) 2019 WngShhng.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

