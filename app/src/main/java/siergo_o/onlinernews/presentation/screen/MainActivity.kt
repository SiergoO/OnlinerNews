package siergo_o.onlinernews.presentation.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import siergo_o.onlinernews.App
import siergo_o.onlinernews.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component.inject(this)
    }
}


