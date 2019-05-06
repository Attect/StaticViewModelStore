package studio.attect.staticviewmodelstore

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class StaticViewModelLifecycleFragment :Fragment(), StaticViewModelStore.StaticViewModelStoreCaller {
    private val staticViewProviderKey:ArrayList<String> = arrayListOf()

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
     */
    override fun <T : ViewModel> getStaticViewModel(viewModelStoreKey: String, cls: Class<out ViewModel>): T? {
        activity?.application?.let {
            staticViewProviderKey.add(viewModelStoreKey)
            return StaticViewModelStore.getViewModelProvider(viewModelStoreKey,it ).get(cls) as T
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.let {
            //清除获取相关ViewModel的key计数
            for (viewProviderKey in staticViewProviderKey) {
                StaticViewModelStore.giveUpViewModelStore(viewProviderKey, it.isChangingConfigurations)
            }
            staticViewProviderKey.clear()
        }

    }




}