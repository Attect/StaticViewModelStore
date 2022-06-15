package studio.attect.staticviewmodelstore.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import studio.attect.staticviewmodelstore.StaticViewModelLifecycleActivity
import studio.attect.staticviewmodelstore.StaticViewModelStore
import studio.attect.staticviewmodelstore.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StaticViewModelStore.StaticViewModelStoreCaller by StaticViewModelLifecycleActivity() {
    //    lateinit var sampleViewModel:SampleViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, SplitActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseStaticViewModel(isChangingConfigurations)
    }
}
