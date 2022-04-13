package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.databinding.FragmentHeadlinesBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class Headlines : Fragment(){
 
    
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, NewsViewModel.Factory(activity.application))
            .get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.newsList.observe(viewLifecycleOwner){
            newslist -> newslist.let {

            val adapter = MainAdapter{
                val action = HeadlinesDirections.actionHeadlinesToNews(it.category.toString(),  it.publishedAt.toString(),  it.url.toString(), it.image, it.description.toString(), it.author)
                findNavController().navigate(action)
            }
            adapter.submitList(it)

            binding.newsRecyler.adapter = adapter
            binding.newsRecyler.layoutManager = LinearLayoutManager(this.requireContext())
            }

            binding.logout?.setOnClickListener {
                AuthUI.getInstance()
                    .signOut(activity as AppCompatActivity)
                    .addOnCompleteListener {
                        // ...
                        Toast.makeText(activity, "Successfully logged out", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.loginRegisteFragment)
                    }
            }
        }
    }

}