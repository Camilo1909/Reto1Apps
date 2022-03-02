package com.example.reto1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reto1.databinding.FragmentPublishBinding

class PublicationFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    var listener: OnNewPublicationListener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPublishBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.publishBtn.setOnClickListener {
            val captionET = binding.captionET.text.toString()
            val publication = Publication("Juan Camilo",captionET,"2 marzo 2020","Cali, Colombia")

            listener?.let {
                it.onNewPublication(publication)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnNewPublicationListener{
        fun onNewPublication(publication:Publication)
    }

    companion object {
        @JvmStatic
        fun newInstance() =  PublicationFragment()
    }
}