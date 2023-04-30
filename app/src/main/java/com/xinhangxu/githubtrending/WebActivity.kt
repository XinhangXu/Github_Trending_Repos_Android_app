package com.xinhangxu.githubtrending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import android.widget.Toast

class WebActivity : AppCompatActivity() {


    lateinit var webView: WebView
    lateinit var loadingBar: RelativeLayout

    var url = MainActivity.selectedRepos.html_url!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        loadingBar = findViewById(R.id.loadingBar)
        webView = findViewById(R.id.webview_load)

        try {

            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
            webView.settings.builtInZoomControls = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    loadingBar.visibility = View.VISIBLE
                    view.loadUrl(url)

                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    loadingBar.visibility = View.GONE
                }
            }
            webView.loadUrl(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "Sorry, error: " + e.toString(), Toast.LENGTH_LONG).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}