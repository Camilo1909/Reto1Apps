package com.example.reto1

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.DateFormat
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import com.example.reto1.databinding.FragmentPublishBinding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class PublicationFragment : Fragment() {

    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!

    private var file:File? = null
    private var URI:String = ""

    var listener: OnNewPublicationListener?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPublishBinding.inflate(inflater,container,false)
        val view = binding.root

        val context = activity as NavigationActivity

        val cameralauncher = registerForActivityResult(StartActivityForResult(), ::onCameraResult)
        val gallerylauncher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)

        binding.cameraBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            file = File("${context.getExternalFilesDir(null)}/photo.png")
            val uri = FileProvider.getUriForFile(context, context.packageName,file!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
            this.URI = uri.toString()
            Log.e(">>>",file?.path.toString())
            cameralauncher.launch(intent)
        }

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            gallerylauncher.launch(intent)
        }

        var citys = ArrayList<String>()
        citys.add("Bogota")
        citys.add("Cali")
        citys.add("Cartagena")
        citys.add("Medellin")
        citys.add("San Andres")

        var spinnerAdapter = ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,citys)

        binding.spinner.setAdapter(spinnerAdapter)

        binding.publishBtn.setOnClickListener{
            val city = binding.spinner.selectedItem.toString()
            val captionET = binding.captionET.text.toString()
            val myDate = Calendar.getInstance().time
            val myString = DateFormat.getDateInstance().format(myDate);
            listener?.let {
                val publication = Publication(context.user.name,captionET,myString,"${city}, Colombia",URI,context.user.imgProfile)
                it.onNewPublication(publication)
            }
            URI = ""
        }
        return view
    }

    fun onCameraResult(result: ActivityResult){
       // val bitmap = result.data?.extras?.get("data") as Bitmap
       // binding.photoIMG.setImageBitmap(bitmap)
        if(result.resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumball = Bitmap.createScaledBitmap(bitmap, bitmap.width/4,bitmap.height/4,true)
            binding.photoIMG.setImageBitmap(thumball)
        }else if(result.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(activity,"No tomo la foto",Toast.LENGTH_SHORT).show()
        }
    }

    fun onGalleryResult(result: ActivityResult){
        if(result.resultCode == Activity.RESULT_OK){
            val uriImage = result.data?.data
            uriImage?.let {
                binding.photoIMG.setImageURI(uriImage)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   interface OnNewPublicationListener{
       fun onNewPublication(publication: Publication)
   }

    companion object {
        @JvmStatic
        fun newInstance() =  PublicationFragment()
    }
}