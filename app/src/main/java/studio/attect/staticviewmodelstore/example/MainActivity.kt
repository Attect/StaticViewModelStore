package studio.attect.staticviewmodelstore.example

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleActivity

class MainActivity : StaticViewModelLifecycleActivity() {
//    lateinit var sampleViewModel:SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        sampleViewModel = getStaticViewModel("testCustomKey",SampleViewModel::class.java)
//        sampleViewModel.textData.observe(this, Observer {
//            textView.text = "持有数据:${it}"
//        })

        button.setOnClickListener {
            startActivity(Intent(this,SplitActivity::class.java))
        }
    }
}
