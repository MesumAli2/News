package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.news.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_news.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            date.text = getString(R.string.date, navigationArg.date)
            imageView.load(navigationArg.image) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_baseline_image_not_supported_24)
            }
        }
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
            textview7.visibility = View.VISIBLE
            webView.visibility = View.GONE
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
                textview7.visibility = View.GONE
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