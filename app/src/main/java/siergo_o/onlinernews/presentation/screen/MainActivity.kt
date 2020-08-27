package siergo_o.onlinernews.presentation.screen

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import siergo_o.onlinernews.R


class MainActivity: AppCompatActivity() {

    private val tabTitles = listOf("Техно", "Люди", "Авто")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tablayout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tag = tabTitles[position]
            viewPager.setCurrentItem(tab.position, true)
            tab.text = tag
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}


