package com.xinhangxu.githubtrending

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {


    lateinit var imgview_owner: ImageView
    lateinit var txtview_score: TextView
    lateinit var txtview_forks: TextView
    lateinit var txtview_watches: TextView
    lateinit var txtview_name: TextView
    lateinit var txtview_language: TextView
    lateinit var txtview_title: TextView
    lateinit var txtview_description: TextView
    lateinit var txtview_createdon: TextView
    lateinit var txtview_updatedon: TextView
    lateinit var txtview_issuescount: TextView
    lateinit var txtview_browser: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        imgview_owner = findViewById(R.id.detlact_imgview_owner)
        txtview_score = findViewById(R.id.detlact_txtview_score)
        txtview_forks = findViewById(R.id.detlact_txtview_forks)
        txtview_watches = findViewById(R.id.detlact_txtview_watches)
        txtview_name = findViewById(R.id.detlact_txtview_name)
        txtview_language = findViewById(R.id.detlact_txtview_language)
        txtview_title = findViewById(R.id.detlact_txtview_title)
        txtview_description = findViewById(R.id.detlact_txtview_description)
        txtview_createdon = findViewById(R.id.detlact_txtview_createdon)
        txtview_updatedon = findViewById(R.id.detlact_txtview_updatedon)
        txtview_issuescount = findViewById(R.id.detlact_txtview_issuescount)
        txtview_browser = findViewById(R.id.detlact_txtview_browser)

        if(!MainActivity.selectedRepos.owner!!.avatar_url.isNullOrEmpty()){
            Picasso.get()
                .load(MainActivity.selectedRepos.owner!!.avatar_url)
                .into(imgview_owner)
        }

        if(MainActivity.selectedRepos.score.isNullOrEmpty()){
            txtview_score.visibility = View.GONE
        }else{
            txtview_score.visibility = View.VISIBLE
            txtview_score.text = MainActivity.selectedRepos.score.toString()
        }

        if(MainActivity.selectedRepos.forks_count.isNullOrEmpty()){
            txtview_forks.visibility = View.GONE
        }else{
            txtview_forks.visibility = View.VISIBLE
            txtview_forks.text = MainActivity.selectedRepos.forks_count.toString()
        }

        if(MainActivity.selectedRepos.score.isNullOrEmpty()){
            txtview_watches.visibility = View.GONE
        }else{
            txtview_watches.visibility = View.VISIBLE
            txtview_watches.text = MainActivity.selectedRepos.watchers_count.toString()
        }

        if(MainActivity.selectedRepos.owner!!.login.isNullOrEmpty()){
            txtview_name.visibility = View.GONE
        }else{
            txtview_name.visibility = View.VISIBLE
            txtview_name.text = MainActivity.selectedRepos.owner!!.login.toString()
        }

        if(MainActivity.selectedRepos.language.isNullOrEmpty()){
            txtview_language.visibility = View.GONE
        }else{
            txtview_language.visibility = View.VISIBLE
            txtview_language.text = " " + MainActivity.selectedRepos.language.toString()
        }

        if(MainActivity.selectedRepos.name.isNullOrEmpty()){
            txtview_title.visibility = View.GONE
        }else{
            txtview_title.visibility = View.VISIBLE
            txtview_title.text = MainActivity.selectedRepos.name.toString()
        }

        if(MainActivity.selectedRepos.description.isNullOrEmpty()){
            txtview_description.visibility = View.GONE
        }else{
            txtview_description.visibility = View.VISIBLE
            txtview_description.text = MainActivity.selectedRepos.description.toString()
        }

        if(MainActivity.selectedRepos.created_at.isNullOrEmpty()){
            txtview_createdon.visibility = View.GONE
        }else{
            txtview_createdon.visibility = View.VISIBLE
            txtview_createdon.text = MainActivity.selectedRepos.created_at.toString().dropLast(10)
        }

        if(MainActivity.selectedRepos.updated_at.isNullOrEmpty()){
            txtview_updatedon.visibility = View.GONE
        }else{
            txtview_updatedon.visibility = View.VISIBLE
            txtview_updatedon.text = MainActivity.selectedRepos.updated_at.toString().dropLast(10)
        }

        if(MainActivity.selectedRepos.open_issues_count.isNullOrEmpty()){
            txtview_issuescount.visibility = View.GONE
        }else{
            txtview_issuescount.visibility = View.VISIBLE
            txtview_issuescount.text = MainActivity.selectedRepos.open_issues_count.toString()
        }

        if(MainActivity.selectedRepos.html_url.isNullOrEmpty()){
            txtview_browser.visibility = View.GONE
        }else{
            txtview_browser.visibility = View.VISIBLE
            txtview_browser.setOnClickListener {
                val intent = Intent(this@DetailsActivity, WebActivity::class.java)
                startActivity(intent)
                //overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_in)
            }
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