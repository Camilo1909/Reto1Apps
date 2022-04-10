package com.example.reto1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reto1.databinding.FragmentHomeBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment(), PublicationFragment.OnNewPublicationListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private  var adapter = PublicationAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        val publicationRecycler = binding.publicationRecycler
        publicationRecycler.setHasFixedSize(true)
        publicationRecycler.layoutManager = LinearLayoutManager(activity)
        publicationRecycler.adapter = adapter

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onNewPublication(publication: Publication) {
        adapter.addPublication(publication)
    }

    override fun onPause() {
        super.onPause()
        /*val context = activity as NavigationActivity
        if(adapter.getPublications().size != 0){
            val json = Gson().toJson(adapter.getPublications())
            val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().putString("recyclerState", json).apply()
            Log.e(">>>",json)
        }*/

    }

    override fun onResume() {
        super.onResume()
        /*val context = activity as NavigationActivity
        val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
        val json = sharedPref.getString("recyclerState", "NO_DATA")
        if (json != "NO_DATA") {
            val itemType = object : TypeToken<ArrayList<Publication>>() {}.type
            val publications = Gson().fromJson<ArrayList<Publication>>(json, itemType)
            // val publications = Gson().fromJson<ArrayList<Publication>>(json,ArrayList::class.java)
            if (adapter.getPublications()==publications){
                adapter.setPublications(publications)
                Log.e(">>>>>","Esta deserializando")
            }else{
                Log.e(">>>>>","Hay nueva publicacion")
            }
            //adapter.setPublications(publications as ArrayList<Publication>)
            //publicationRecycler = publications
        }*/

    }

    fun getAdapter():PublicationAdapter{
        return adapter
    }
}