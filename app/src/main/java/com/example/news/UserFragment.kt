package com.example.news

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.news.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth


class UserFragment : Fragment() {
    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object{
        val IMAGE_REQUES_CODE = 100
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        (activity as AppCompatActivity).supportActionBar?.title = " ${mAuth.currentUser?.displayName} Profile"
        (activity as AppCompatActivity).supportActionBar?.setLogo(R.drawable.ic_baseline_person_24)
        (activity as AppCompatActivity).supportActionBar?.subtitle =""
        binding.userImage.load(mAuth.currentUser?.photoUrl){
            placeholder(R.drawable.ic_baseline_person_24)
            error(R.drawable.ic_baseline_broken_image_24)
        }
        if (mAuth.currentUser?.photoUrl == null){
                binding.userImage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_REQUES_CODE)
                }


        }
        binding.username.text = mAuth.currentUser?.displayName.toString()
        binding.email.text = mAuth.currentUser?.email.toString()
        binding.phonenumber.text = mAuth.currentUser?.phoneNumber.toString()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if the requestCode matches with imageRequestCode 100 and result code = succeeded
        if (requestCode == IMAGE_REQUES_CODE && resultCode == Activity.RESULT_OK){
            // image_view.setImageURI(data?.data )
            // If image uri is returned from activity result then addSchedule
            populateImage(data?.data)
        }
    }

    private fun populateImage(data: Uri?) {
        binding.userImage.setImageURI(data)

    }


}