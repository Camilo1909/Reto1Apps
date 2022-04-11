package com.example.reto1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.reto1.databinding.FragmentHomeBinding
import com.example.reto1.databinding.FragmentMyProfileBinding
import com.google.gson.Gson
import java.io.File

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private var file:File? = null
    private var URI:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      _binding = FragmentMyProfileBinding.inflate(inflater,container,false)
      val view = binding.root
      val context = activity as NavigationActivity
      binding.nameET.setText(context.user.name)
        if(context.user.imgProfile!=""){
        val uri = Uri.parse(context.user.imgProfile)
            binding.imgPerfil.setImageURI(uri)
        }


      val cameralauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResult)
      val gallerylauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)

      binding.imgPerfil.setOnClickListener {
          val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

          file = File("${context.getExternalFilesDir(null)}/photo.png")
          val uri = FileProvider.getUriForFile(context, context.packageName,file!!)
          intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
          this.URI = uri.toString()
          Log.e(">>>",file?.path.toString())
          cameralauncher.launch(intent)
      }

        binding.btGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            gallerylauncher.launch(intent)
        }

        binding.btEdit.setOnClickListener {
            context.user.name = binding.nameET.getText().toString()
            context.user.imgProfile = URI
        }

        binding.btLogout.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            intent.putExtra("logUser", Gson().toJson(context.user))
            startActivity(intent)
        }

      return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyProfileFragment()
    }

    fun onCameraResult(result: ActivityResult){
        // val bitmap = result.data?.extras?.get("data") as Bitmap
        // binding.photoIMG.setImageBitmap(bitmap)
        if(result.resultCode == Activity.RESULT_OK){
            val bitmap = BitmapFactory.decodeFile(file?.path)
            val thumball = Bitmap.createScaledBitmap(bitmap, bitmap.width/4,bitmap.height/4,true)
            binding.imgPerfil.setImageBitmap(thumball)
        }else if(result.resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(activity,"No tomo la foto", Toast.LENGTH_SHORT).show()
        }
    }

    fun onGalleryResult(result: ActivityResult){
        if(result.resultCode == Activity.RESULT_OK){
            val uriImage = result.data?.data

            uriImage?.let {
                binding.imgPerfil.setImageURI(uriImage)
            }

        }
    }
    override fun onResume() {
        super.onResume()
    }
}