package studio.attect.staticviewmodelstore.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import studio.attect.staticviewmodelstore.StaticViewModelHolder
import studio.attect.staticviewmodelstore.StaticViewModelStore
import studio.attect.staticviewmodelstore.example.databinding.FragmentTopBinding

/**
 * 直接委托实现StaticViewModelStoreCaller的Fragment
 */
class TopFragment : Fragment(), StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelHolder() {
    private lateinit var binding: FragmentTopBinding
    private var sampleViewModel: SampleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sampleViewModel = getStaticViewModel("testCustomKey", SampleViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleViewModel?.textData?.observe(viewLifecycleOwner, Observer {
            applyViewModelData(it)
        })
        applyViewModelData(sampleViewModel?.textData?.value)
    }

    private fun applyViewModelData(text: String?) {
        if (text != null) {
            binding.textView.text = "持有数据:${text}"
        } else {
            binding.textView.text = "没有数据"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!requireActivity().isChangingConfigurations) {
            releaseStaticViewModel()
        }
    }

}
