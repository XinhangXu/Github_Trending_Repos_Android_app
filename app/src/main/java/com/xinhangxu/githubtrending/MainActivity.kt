package com.xinhangxu.githubtrending

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat.setOnActionExpandListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.xinhangxu.githubtrending.Adapters.ReposItemsAdapter
import com.xinhangxu.githubtrending.Models.ReposItemsModel
import com.xinhangxu.githubtrending.utils.UtilValidate
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var loadingBar: RelativeLayout
    lateinit var recyclerView_Repos: RecyclerView
    lateinit var progressBar: RelativeLayout






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadingBar = findViewById(R.id.loadingBar)
        recyclerView_Repos = findViewById(R.id.mainact_recylerview_repos)
        progressBar = findViewById(R.id.progressBarLayout)

        callapi_getGithubTrendingRepos()

        recyclerView_Repos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    var lastIndex = vLayoutManagaer.findLastVisibleItemPosition()
                    if(lastIndex == itemsList.size-1 && lastIndex != loadedIndex){
                        loadedIndex = lastIndex
                        index_Page = index_Page + 1
                        callapi_getGithubTrendingRepos()
                    }
                }
            }
        })


    }




/*    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.title_action_search)
        val searchView: SearchView = searchItem.getActionView() as SearchView


        searchView.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                reposAdapter = ReposItemsAdapter(this@MainActivity, itemsList)
                vLayoutManagaer = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                recyclerView_Repos.layoutManager = vLayoutManagaer
                recyclerView_Repos.itemAnimator = DefaultItemAnimator()
                recyclerView_Repos.adapter = reposAdapter
                return true
            }
        })

        searchView.setOnCloseListener {

            ture
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    var filterList = arrayListOf<ReposItemsModel>()
                    for(i in itemsList){
                        if(i.language.toString().toLowerCase().contains(p0)
                            || i.description.toString().toLowerCase().contains(p0)
                            || i.updated_at.toString().toLowerCase().contains(p0)
                            || i.name.toString().toLowerCase().contains(p0)
                            || i.owner!!.login.toString().toLowerCase().contains(p0)
                        ){
                            filterList.add(i)
                        }
                    }
                    if(filterList.size < 1){
                        Toast.makeText(this@MainActivity, "No matched results!", Toast.LENGTH_LONG).show()
                    }else{
                        reposAdapter = ReposItemsAdapter(this@MainActivity, filterList)
                        vLayoutManagaer = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        recyclerView_Repos.layoutManager = vLayoutManagaer
                        recyclerView_Repos.itemAnimator = DefaultItemAnimator()
                        recyclerView_Repos.adapter = reposAdapter
                    }
                }
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                return false
            }

        })
        return true
    }*/


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.title_action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    var filterList = arrayListOf<ReposItemsModel>()
                    for(i in itemsList){
                        if(i.language.toString().toLowerCase().contains(p0.toLowerCase())
                            || i.description.toString().toLowerCase().contains(p0.toLowerCase())
                            || i.updated_at.toString().toLowerCase().contains(p0.toLowerCase())
                            || i.name.toString().toLowerCase().contains(p0.toLowerCase())
                            || i.owner!!.login.toString().toLowerCase().contains(p0.toLowerCase())
                        ){
                            filterList.add(i)
                        }
                    }
                    if(filterList.size < 1){
                        Toast.makeText(this@MainActivity, "No matched results!", Toast.LENGTH_LONG).show()
                    }else{
                        reposAdapter = ReposItemsAdapter(this@MainActivity, filterList)
                        vLayoutManagaer = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        recyclerView_Repos.layoutManager = vLayoutManagaer
                        recyclerView_Repos.itemAnimator = DefaultItemAnimator()
                        recyclerView_Repos.adapter = reposAdapter
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do something with the new text
                return true
            }
        })

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchView.setQuery("", false)
                searchItem.collapseActionView()
            }
        }

        searchView.setOnCloseListener {
            reposAdapter = ReposItemsAdapter(this@MainActivity, itemsList)
            vLayoutManagaer = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView_Repos.layoutManager = vLayoutManagaer
            recyclerView_Repos.itemAnimator = DefaultItemAnimator()
            recyclerView_Repos.adapter = reposAdapter
            //onBackPressed()
            true
        }

        return super.onCreateOptionsMenu(menu)
    }




    fun callapi_getGithubTrendingRepos(){

        try {
            if(index_Page == 1){
                loadingBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.VISIBLE
            }
            if (UtilValidate.isConnectivity(this@MainActivity)) {
                RestServiceBuilder.apiService!!.getGithubTrendingRepos("Q", index_Page,ITEM_COUNT)
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            Log.e("API_url", "" + call.request().url())

                            if (response.isSuccessful) {
                                val responseString = response.body()!!.toString().substring(1, response.body()!!.toString().length - 1)
                                val jsonFormattedString = responseString.replace("\\\\".toRegex(), "")
                                val maxLogSize = 1000
                                val mystr = jsonFormattedString
                                val stringLength = mystr.length
                                for (i in 0..stringLength / maxLogSize) {
                                    val start = i * maxLogSize
                                    var end = (i + 1) * maxLogSize
                                    end = if (end > mystr.length) mystr.length else end
                                    Log.v("Response_repos", mystr.substring(start, end))
                                }

                                try {

                                    var jsonObject = JSONObject(response.body()!!.toString())

                                    if(jsonObject.getString("incomplete_results") == "false"){

                                        try{
                                            //itemsList = arrayListOf()
                                            var jsonarray = jsonObject.getJSONArray("items")
                                            val type1 = object : TypeToken<ArrayList<ReposItemsModel>>() {}.type
                                            if(index_Page == 1){
                                                itemsList = Gson().fromJson<ArrayList<ReposItemsModel>>(jsonarray.toString(), type1)
                                                reposAdapter = ReposItemsAdapter(this@MainActivity, itemsList)
                                                vLayoutManagaer = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                                                recyclerView_Repos.layoutManager = vLayoutManagaer
                                                recyclerView_Repos.itemAnimator = DefaultItemAnimator()
                                                recyclerView_Repos.adapter = reposAdapter
                                            }else{
                                                var tempList = arrayListOf<ReposItemsModel>()
                                                tempList = Gson().fromJson<ArrayList<ReposItemsModel>>(jsonarray.toString(), type1)
                                                for(i in tempList){
                                                    itemsList.add(i)
                                                }
                                                //itemsList = (itemsList + tempList) as ArrayList<ReposItemsModel>
                                                /*val recyclerViewState = recyclerView_Repos.layoutManager?.onSaveInstanceState()
                                                reposAdapter.notifyDataSetChanged()
                                                recyclerView_Repos.layoutManager?.onRestoreInstanceState(recyclerViewState)*/
                                                reposAdapter.notifyDataSetChanged()
                                                Log.e("=====","==============size  " + itemsList.size.toString())
                                            }




                                        }catch (e:java.lang.Exception){
                                            e.printStackTrace()
                                        }



                                    }else{
                                        Toast.makeText(this@MainActivity, "API Responses Error: incomplete_results - "+jsonObject.getString("incomplete_results"), Toast.LENGTH_LONG).show()
                                    }
                                    if(index_Page == 1){
                                        loadingBar.visibility = View.GONE
                                    }else{
                                        progressBar.visibility = View.GONE
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    if(index_Page == 1){
                                        loadingBar.visibility = View.GONE
                                    }else{
                                        progressBar.visibility = View.GONE
                                    }
                                }
                            } else {
                                Log.e("Error12", response.errorBody().toString())
                                if(index_Page == 1){
                                    loadingBar.visibility = View.GONE
                                }else{
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            Log.e("Error11", t.toString())
                            if(index_Page == 1){
                                loadingBar.visibility = View.GONE
                            }else{
                                progressBar.visibility = View.GONE
                            }
                        }
                    })

            } else {
                if(index_Page == 1){
                    loadingBar.visibility = View.GONE
                }else{
                    progressBar.visibility = View.GONE
                }
                Toast.makeText(this@MainActivity, "Not internet!", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            if(index_Page == 1){
                loadingBar.visibility = View.GONE
            }else{
                progressBar.visibility = View.GONE
            }
        }

    }



    companion object{
        var itemsList = arrayListOf<ReposItemsModel>()
        var selectedRepos = ReposItemsModel()
        lateinit var reposAdapter: ReposItemsAdapter
        lateinit var vLayoutManagaer: LinearLayoutManager

        var loadedIndex = 0

        var index_Page = 1
        var ITEM_COUNT = 20
    }




}