package studio.attect.staticviewmodelstore

import androidx.lifecycle.LifecycleService

open class StaticViewModelLifecycleService : LifecycleService(), StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelHolder() {

    override fun onDestroy() {
        super.onDestroy()
        releaseStaticViewModel()
    }

}