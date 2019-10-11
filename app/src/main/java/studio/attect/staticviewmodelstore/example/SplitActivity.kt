package studio.attect.staticviewmodelstore.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split)
        if(supportFragmentManager.findFragmentById(R.id.fragmentContainerTop)== null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerTop,TopFragment()).add(R.id.fragmentContainerBottom,BottomFragment()).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        title = "Split Activity"
    }
}
