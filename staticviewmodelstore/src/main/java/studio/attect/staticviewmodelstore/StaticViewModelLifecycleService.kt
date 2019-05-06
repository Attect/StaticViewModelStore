package studio.attect.staticviewmodelstore

import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModel
import java.util.ArrayList

open class StaticViewModelLifecycleService : LifecycleService(), StaticViewModelStore.StaticViewModelStoreCaller {
    private val staticViewProviderKey = ArrayList<String>()

    /**
     * 获得一个全局ViewModel
     * 此ViewModel可以跨越Activity、Fragment以及Service
     * 将被StaticViewModelStore持有
     * 此方法应该在OnCreate之时及之后再使用
     *
     * @param viewModelStoreKey 区分ViewModelStore的key，同一个key下的ViewModel将能收到同一个事件
     * @param cls               ViewModel实现类的class
     * @param <T>               ViewModel实现类型
     * @return 请求的ViewModel
    </T> */
    override fun <T : ViewModel> getStaticViewModel(viewModelStoreKey: String, cls: Class<out ViewModel>): T {
        staticViewProviderKey.add(viewModelStoreKey)
        return StaticViewModelStore.getViewModelProvider(viewModelStoreKey, application).get(cls) as T
    }

    override fun onDestroy() {
        super.onDestroy()

        //清除获取相关ViewModel的key计数
        for (viewProviderKey in staticViewProviderKey) {
            StaticViewModelStore.giveUpViewModelStore(
                viewProviderKey,
                false
            ) //每个Service只有一个实例，如果数据不被其它Activity或Fragment持有，自身也Destroy时，即视为此数据不被任何对象使用
        }
        staticViewProviderKey.clear()
    }

}