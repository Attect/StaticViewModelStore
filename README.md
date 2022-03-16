# StaticViewModelStore
安卓跨Activity/Fragment/Service间即时传递数据，基于安卓MVVM，支持分屏&文档模式，支持自动释放

解决以下需求：

1. 多个Activity间进行数据观察观察
1. 跨多个Activity的各个Fragment间进行同一个数据观察
1. 同时在多个Activity间、多个Fragment间、多个Service间混合跨度对同一数据进行观察
 
相关演示：https://www.bilibili.com/video/av51635073/

## 使用方法

详见源码中app例子

#### Application

必要初始化

```kotlin
override fun onCreate() {
    super.onCreate()
    StaticViewModelStore.application = this
}
```

#### Activity

继承方式

```kotlin
class MyActivity : StaticViewModelLifecycleActivity() {}
```

委托方式

```kotlin
class MyActivity : AppCompatActivity(),
    StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelLifecycleActivity() {
    override fun onDestroy() {
        super.onDestroy()
        releaseStaticViewModel(isChangingConfigurations) //别忘了这个，忘了就内存泄漏
    }
}
```

#### Fragment

继承方式

```kotlin
class MyFragment : StaticViewModelLifecycleFragment() {}
```

委托方式

```kotlin
class MyFragment : Fragment(),
    StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelLifecycleFragment() {
    override fun onDestroy() {
        super.onDestroy()
        releaseStaticViewModel(requireActivity().isChangingConfigurations) //别忘了这个，忘了就内存泄漏
    }
}
```

#### Service

继承方式

```kotlin
class MyService : StaticViewModelLifecycleService() {}
```

委托方式

```kotlin
class MyService : LifecycleService(),
    StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelLifecycleFragment() {
    override fun onDestroy() {
        super.onDestroy()
        releaseStaticViewModel() //别忘了这个，忘了就内存泄漏
    }
}
```