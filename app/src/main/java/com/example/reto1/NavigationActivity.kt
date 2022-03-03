package com.example.reto1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.reto1.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var publicationFragment: PublicationFragment
    private lateinit var myProfileFragment: MyProfileFragment

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_navigation)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        homeFragment = HomeFragment.newInstance()
        publicationFragment = PublicationFragment.newInstance()
        myProfileFragment = MyProfileFragment.newInstance()

        showFragment(homeFragment)

        binding.navigatorBtn.setBackgroundColor(8)

        binding.navigatorBtn.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.homeItem) {
                showFragment(homeFragment)
            } else if (menuItem.itemId == R.id.publishItem) {
                showFragment(publicationFragment)
            }else if (menuItem.itemId == R.id.profileItem) {
                showFragment(myProfileFragment)
            }
            true
        }
    }

    fun showFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
}