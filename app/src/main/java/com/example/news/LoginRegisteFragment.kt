package com.example.news

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentLoginRegisteBinding
import com.example.news.domain.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Response
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.security.auth.callback.Callback


class LoginRegisteFragment : Fragment() {

    private var _binding : FragmentLoginRegisteBinding? = null
    val binding get() = _binding!!
    private lateinit var mAuth : FirebaseAuth
    //Sing in launch which revices a callback with the user resposne
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginRegisteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        mAuth = FirebaseAuth.getInstance()

            startSignIn()
    }


    private fun startSignIn(){
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build())

    // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLauncher.launch(signInIntent)

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            //Firebase firestore setup
            val rootRef: FirebaseFirestore = FirebaseFirestore.getInstance()
            val productRef: CollectionReference = rootRef.collection("users")
            productRef.get().addOnCompleteListener {
                val response = com.example.news.domain.Response()

                if (it.isSuccessful) {
                    val result = it.result
                    result.let {
                      //Converts each response into user object
                      response.users = result.documents.mapNotNull {
                          it.toObject(User::class.java)
                      }
                    }
                }
                var userExists : Boolean = false
                for (i  in response.users!!){
                    //if users is found in db then set userExists to true
                    if (i.uid == (mAuth.currentUser?.uid)){
                        userExists = true
                        Toast.makeText(activity, "Welcome Back ${mAuth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                    }
                }
                //if user not exists then save new user
                if (!userExists){
                    Toast.makeText(activity, "New user saved", Toast.LENGTH_SHORT).show()
                    saveNewUser()
                }
            }

                val action = LoginRegisteFragmentDirections.actionLoginRegisteFragmentToHeadlines()
                findNavController().navigate(action)

                // ...
            }
        }


    private fun saveNewUser() {
        val db = Firebase.firestore
        val user = User(mAuth.currentUser?.uid,mAuth.currentUser?.displayName.toString(), mAuth.currentUser?.email.toString())
        db.collection("users")
            .document(mAuth.uid!!)
            .set(user).addOnSuccessListener() {
                Toast.makeText(activity, "user added successfully ", Toast.LENGTH_SHORT).show()
            }
    }



}