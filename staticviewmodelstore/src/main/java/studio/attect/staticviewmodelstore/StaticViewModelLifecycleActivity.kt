package studio.attect.staticviewmodelstore


import androidx.appcompat.app.AppCompatActivity

open class StaticViewModelLifecycleActivity : AppCompatActivity(), StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelHolder() {

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            releaseStaticViewModel()
        }
    }

}