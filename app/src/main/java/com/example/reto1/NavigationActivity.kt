package com.example.reto1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.reto1.databinding.ActivityNavigationBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NavigationActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var publicationFragment: PublicationFragment
    private lateinit var myProfileFragment: MyProfileFragment

    private lateinit var binding: ActivityNavigationBinding

    public lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        val logUser = intent.getStringExtra("logUser")
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_navigation)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        user = Gson().fromJson(logUser,User::class.java)

        homeFragment = HomeFragment.newInstance()
        publicationFragment = PublicationFragment.newInstance()
        myProfileFragment = MyProfileFragment.newInstance()

        publicationFragment.listener = homeFragment

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

    override fun onPause() {
        super.onPause()
        if(homeFragment.getAdapter().getPublications().size != 0){
            val json = Gson().toJson(homeFragment.getAdapter().getPublications())
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().putString("recyclerState", json).apply()
            Log.e(">>>",json)
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val json = sharedPref.getString("recyclerState", "NO_DATA")
        if (json != "NO_DATA") {
            val itemType = object : TypeToken<ArrayList<Publication>>() {}.type
            val publications = Gson().fromJson<ArrayList<Publication>>(json, itemType)
            // val publications = Gson().fromJson<ArrayList<Publication>>(json,ArrayList::class.java)
            if (homeFragment.getAdapter().getPublications()!=publications){
                homeFragment.getAdapter().setPublications(publications)
                Log.e(">>>>>","Esta deserializando")
            }else{
                Log.e(">>>>>","Hay nueva publicacion")
            }
            //adapter.setPublications(publications as ArrayList<Publication>)
            //publicationRecycler = publications
        }
    }
}