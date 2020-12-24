package com.assignm.wikiimagesearch

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.assignm.wikiimagesearch.Adapter.SearchResultsAdapter
import com.assignm.wikiimagesearch.model.Pages
import com.assignm.wikiimagesearch.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mainActivityViewModel: MainActivityViewModel?=null
    var adapter: SearchResultsAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel=
                ViewModelProvider.AndroidViewModelFactory(applicationContext as Application).create(MainActivityViewModel::class.java)
        search_results.setHasFixedSize(true)
        search_results.layoutManager= GridLayoutManager(this,2)
        search_query.requestFocus()
        search_query.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(isOnline()){
                    adapter?.clearData()
                    if(query.isEmpty()){
                        no_results.visibility= View.VISIBLE
                        search_results.visibility= View.GONE
                    }else{
                        observeData(query)
                    }
                }else{
                    noInternetDialog()
                }

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    hideKeyboard(search_query)
                    adapter?.clearData()
                    no_results.visibility= View.VISIBLE
                    search_results.visibility= View.GONE
                }
                return true
            }
        })
    }

    fun observeData(searchQuery:String):Unit{
        adapter?.clearData()
        mainActivityViewModel!!.getLiveDataSearchResponse(searchQuery,500)!!.observe(this,
                Observer<ArrayList<Pages>>(
                ){
                    if(it!=null){
                        if(it.size>0){
                            no_results.visibility= View.GONE
                            search_results.visibility=View.VISIBLE
                            adapter=SearchResultsAdapter(it,applicationContext as Application)
                            search_results.adapter=adapter
                            adapter?.notifyDataSetChanged()
                        }else{
                            no_results.visibility= View.VISIBLE
                            search_results.visibility=View.GONE
                        }
                    }else{
                        no_results.visibility= View.VISIBLE
                        search_results.visibility=View.GONE
                    }
                });
    }

    fun isOnline(): Boolean {
        var result = false
        val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }

            }
        }
        return result
    }

    fun noInternetDialog():Unit{
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.no_internet)
        builder.setMessage(R.string.no_internet_message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Ok"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onPause() {
        super.onPause()
        search_query.clearFocus()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.exit_app)
        builder.setMessage(R.string.exit_app_permission)
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            dialogInterface.dismiss()
            finish()
        }
        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}