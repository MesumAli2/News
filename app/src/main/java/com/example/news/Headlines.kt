package com.example.news

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.databinding.FragmentHeadlinesBinding
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class Headlines : Fragment(){

    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
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
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar?.setLogo(R.drawable.news)
        (activity as AppCompatActivity).supportActionBar?.title = " Mega news"
        (activity as AppCompatActivity).supportActionBar?.subtitle = "  Trusted worldwide "

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Save the news data from network to database
        viewModel.newsNetworkDataRepo.observe(viewLifecycleOwner){
            viewModel.insertAllNews(it)
            }
        val adapter = MainAdapter {
            val action = HeadlinesDirections.actionHeadlinesToNews(
                it.category.toString(),
                it.publishedAt.toString(),
                it.url.toString(),
                it.images,
                it.description.toString(),
                it.author,
                it.source
            )
            findNavController().navigate(action)
        }
        binding.newsRecyler.adapter = adapter
        binding.newsRecyler.layoutManager = LinearLayoutManager(this@Headlines.context)
        //Load the news from the database
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getnewsdatasource(search = "").collect {
                Log.d("DataCheck ", it.toString())
                adapter.submitData(it)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        inflater.inflate(R.menu.nav_header_menu, menu)
        inflater.inflate(R.menu.person_menu, menu)
        createSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.lgtbtn ->{
                logout()
            }
            R.id.person_menu_icon ->{
                val action = HeadlinesDirections.actionHeadlinesToUserFragment()
                findNavController().navigate(action)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(activity as AppCompatActivity)
            .addOnCompleteListener {
                Toast.makeText(activity, "Successfully logged out", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginRegisteFragment)
            }
    }

    private fun createSearchView(menu: Menu) {
        val searchMeuItem = menu.findItem(R.id.menu_search)
        val searchView = searchMeuItem?.actionView as? SearchView
        val keyboard = (activity as AppCompatActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        searchView?.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{

            val adapter = MainAdapter {
                val action = HeadlinesDirections.actionHeadlinesToNews(
                    it.category.toString(),
                    it.publishedAt.toString(),
                    it.url.toString(),
                    it.images,
                    it.description.toString(),
                    it.author,
                    it.source
                )
                findNavController().navigate(action)
            }
            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.serachNews(query.toString())
                binding.newsRecyler.adapter = adapter
                binding.newsRecyler.layoutManager = LinearLayoutManager(this@Headlines.context)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getnewsdatasource(search = query!!).collect {
Log.d("DataCheck ", it.toString())
                        adapter.submitData(it)
                    }
                }

                Toast.makeText(activity, "${query.toString()}", Toast.LENGTH_SHORT).show()
                keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    viewModel.refreshDataFromRepository()
                        //  viewModel.getnewsdatasource(newText).observe(viewLifecycleOwner){
                        //  val adapter = MainAdapter{}
                      //  adapter.submitData(it)
                  //  }
                    adapter.refresh()

                    keyboard.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

        } )
    }




}