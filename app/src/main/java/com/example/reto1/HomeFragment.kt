package com.example.reto1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reto1.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), PublicationFragment.OnNewPublicationListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter = PublicationAdapter()

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
}