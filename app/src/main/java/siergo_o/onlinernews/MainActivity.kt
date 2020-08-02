package siergo_o.onlinernews;

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import siergo_o.onlinernews.view.FragmentNewsHolder
import java.util.*


class MainActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main);

        val viewPager: ViewPager = findViewById(R.id.viewpager);
        viewPager.offscreenPageLimit = 3
        val pagerAdapter = PagerAdapter(supportFragmentManager, this);
        viewPager.adapter = pagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        for (i in 0..tabLayout.size) {
            val tab = tabLayout.getTabAt(i)
            tab?.customView = pagerAdapter.getTabView(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    inner class PagerAdapter(fm: FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {

        private val tabTitles = listOf("Техно", "Люди", "Авто")
        private var tv: TextView? = null
        private var tab: View? = null

        override fun getCount(): Int = tabTitles.size

        override fun getItem(position: Int): Fragment =
                when (position) {
                    0 -> FragmentNewsHolder("https://tech.onliner.by/")
                    1 -> FragmentNewsHolder("https://people.onliner.by/")
                    2 -> FragmentNewsHolder("https://auto.onliner.by//")
                    else -> FragmentNewsHolder("https://tech.onliner.by/")
                }

        override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]

        @SuppressLint("InflateParams")
        fun getTabView(position: Int): View? {
            tab = LayoutInflater.from(context).inflate(R.layout.custom_tab,null)
            tv = tab?.findViewById(R.id.title_tab_text)
            tv?.text = tabTitles[position];
            return tab
        }
    }

}


