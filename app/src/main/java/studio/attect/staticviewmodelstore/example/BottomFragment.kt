package studio.attect.staticviewmodelstore.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleFragment
import studio.attect.staticviewmodelstore.example.databinding.FragmentBottomBinding

class BottomFragment : StaticViewModelLifecycleFragment() {
    private lateinit var binding: FragmentBottomBinding
    private var sampleViewModel: SampleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sampleViewModel = getStaticViewModel("testCustomKey", SampleViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleViewModel?.textData?.observe(viewLifecycleOwner, Observer {
            applyViewModelData(it)
        })
        applyViewModelData(sampleViewModel?.textData?.value)
        binding.button.setOnClickListener {
            sampleViewModel?.textData?.value = binding.editText.text.toString()
        }
    }

    private fun applyViewModelData(text: String?) {
        if (text != null) {
            binding.editText.setText(text)
        } else {
            binding.editText.setText("")
        }
    }
}