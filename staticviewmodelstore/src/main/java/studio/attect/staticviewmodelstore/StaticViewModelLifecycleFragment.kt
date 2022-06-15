package studio.attect.staticviewmodelstore

import androidx.fragment.app.Fragment

open class StaticViewModelLifecycleFragment : Fragment(), StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelHolder() {
    override fun onDestroy() {
        super.onDestroy()

        if (!requireActivity().isChangingConfigurations) {
            releaseStaticViewModel()
        }
    }
}