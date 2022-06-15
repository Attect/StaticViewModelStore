package studio.attect.staticviewmodelstore

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

/**
 * 带生命周期的无障碍服务
 *
 * 此设计不支持委托模式
 */
abstract class StaticViewModelLifecycleAccessibilityService : AccessibilityService(),
    LifecycleOwner,
    StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelHolder() {

    private val mDispatcher by lazy { ServiceLifecycleDispatcher(this) }


    @CallSuper
    override fun onCreate() {
        mDispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    @CallSuper
    override fun onServiceConnected() {
        mDispatcher.onServicePreSuperOnBind()
        super.onServiceConnected()
    }

    @Suppress("DEPRECATION")
    @CallSuper
    override fun onStart(intent: Intent?, startId: Int) {
        mDispatcher.onServicePreSuperOnStart()
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        mDispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
        releaseStaticViewModel()
    }

    override fun getLifecycle(): Lifecycle = mDispatcher.lifecycle

}