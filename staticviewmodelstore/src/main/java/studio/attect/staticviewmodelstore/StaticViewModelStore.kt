package studio.attect.staticviewmodelstore

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import java.util.HashMap

/**
 * 单例ViewModelStore
 * 用于实现不同Activity和Fragment间的数据通信
 * 甚至Service和Activity与Fragment间的通信(暂未验证)
 *
 *
 * 使用方法：不应该直接调用此类，应改在Activity和Fragment以下方法调用（ todo Service待做)
 *
 * @author attect
 * @date 2019年5月6日
 */
object StaticViewModelStore {
    private val TAG = "SVMS"
    private val viewModelStoreMap = HashMap<String, ViewModelStore>()
    private val viewModelProviderCounter = HashMap<String, Int>()

    /**
     * 从给定的全局ViewModelStore中创建一个ViewModelProvider以用于获取ViewModel实例
     *
     * @param key         ViewModelProvider的指定key
     * @param application Android Application，用于创建ViewModel的Factory
     * @return 可创建在指定key的ViewModelStore中持有的ViewModel的ViewModelProvider
     */
    fun getViewModelProvider(key: String, application: Application): ViewModelProvider {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        var viewModelStore = viewModelStoreMap[key]
        if (viewModelStore == null) {
            viewModelStore = ViewModelStore()
            viewModelStoreMap[key] = viewModelStore
        }

        //更新计数
        var count = viewModelProviderCounter[key]
        if (count != null) {
            count++
        } else {
            count = 1
        }
        viewModelProviderCounter[key] = count

        Log.d(TAG,"new visitor to store:${key} now customer(s):${count}")
        return ViewModelProvider(viewModelStore, factory)

    }

    /**
     * ViewModel持有者放弃相关key的ViewModel
     * 当持有数量为零时，如果Activity在reconfig，则不做其它操作
     * 否则将从全局viewModelStoreMap中彻底移除相关ViewModelStore
     *
     * @param key                      持有ViewModel的ViewModelStore
     * @param isChangingConfigurations Activity是否正在变更配置,传入Activity.isChangingConfigurations()方法结果，若为Fragment或Service，则始终为false
     */
    fun giveUpViewModelStore(key: String, isChangingConfigurations: Boolean) {
        //Activity或Fragment可能持有但本次并未使用任何ViewModel
        var count = viewModelProviderCounter[key]
        if (count != null) {
            count--
            viewModelProviderCounter[key] = count
            Log.d(TAG,"a customer leave store ${key} , left ${count} customer(s)")
            if (count == 0 && !isChangingConfigurations) {
                viewModelProviderCounter.remove(key)
                val viewModelStore = viewModelStoreMap[key]
                if (viewModelStore != null) {
                    viewModelStore.clear()
                    viewModelStoreMap.remove(key)
                    Log.i(TAG,"remove viewModelStore:${key}")
                }
            }else if (!isChangingConfigurations){
                Log.i(TAG,"keep viewModelStore:${key} because activity is changing configurations")
            }
        }
    }

    interface StaticViewModelStoreCaller {
        fun <T : ViewModel> getStaticViewModel(viewModelStoreKey: String, cls: Class<out ViewModel>): T?
    }
}
