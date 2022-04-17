package com.example.news

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.news.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_news.*
import java.text.SimpleDateFormat
import java.util.*


//Displayed news detail for users selected news item from the list
class News : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val navigationArg : NewsArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyboard = (activity as AppCompatActivity).getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        (activity as AppCompatActivity).supportActionBar?.title = "Article"
        showNewsDetail()
        viewFullArticle()
        hideArticleFromView()
        webViewSetUp()
    }


    private fun showNewsDetail() {
        // binding.textView.text = navigationArg.namearticle.toString()
        binding.apply {
            category.text = getString(R.string.category, navigationArg.category)
            articledescription.text = navigationArg.description.toString()
            author.text = navigationArg.author
            Log.d("newsfragment", navigationArg.date.toString())
           // date.text = getString(R.string.date, navigationArg.date)
            imageView.load(navigationArg.image) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_baseline_image_not_supported_24)
            }
            if (navigationArg.image.isNullOrEmpty() && navigationArg.source.contains("bbc", ignoreCase = true) ){
                imageView.setImageResource(R.drawable.bbc)
            }
            if ( navigationArg.image.isNullOrEmpty() && navigationArg.source.contains("bloomberg", ignoreCase = true)){
                imageView.setImageResource(R.drawable.bloombergicon)
            }
        }
        formatDate()
    }

    private fun formatDate(){
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+ss:ss")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputTimeFormat = SimpleDateFormat("HH:mm")
        val date: Date = inputFormat.parse(navigationArg.date)
        val formattedDate: String = outputFormat.format(date)
        val formatedTime : String = outputTimeFormat.format(date)
        println(formattedDate) // prints 10-04-2018
        binding.date.text = formattedDate.toString()
        binding.time.text = formatedTime


    }

    private fun hideArticleFromView() {
        hidearticle.setOnClickListener {
            category.visibility = View.VISIBLE
            articledescription.visibility = View.VISIBLE
            author.visibility = View.VISIBLE
            date.visibility = View.VISIBLE
            imageView.visibility = View.VISIBLE
            showarticle.visibility =View.VISIBLE
            hidearticle.visibility = View.GONE
            webView.visibility = View.GONE
            hidearticle.setTextColor(Color.BLACK)
        }
    }

    private fun viewFullArticle() {
        binding.apply {
            showarticle.setOnClickListener {
                webView.visibility = View.VISIBLE
                hidearticle.visibility = View.VISIBLE
                category.visibility = View.GONE
                articledescription.visibility = View.GONE
                author.visibility = View.GONE
                date.visibility = View.GONE
                imageView.visibility = View.GONE
                showarticle.visibility = View.GONE
            }
        }
    }

    //Creates webview to view the full news article
    private fun webViewSetUp() {
        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.apply {
                loadUrl(navigationArg.urlstring)
                settings.javaScriptEnabled = true
            }
        }

    }

}