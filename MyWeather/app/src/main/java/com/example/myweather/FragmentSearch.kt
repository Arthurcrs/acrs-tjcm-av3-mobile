package com.example.myweather

import CityWeather
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather.api.WeatherResponse
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.addAll

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSearch : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var myview: View
    lateinit var searchButton: Button
    lateinit var searchCity: EditText
    lateinit var progressBar: ProgressBar

    lateinit var recyclerView: RecyclerView

    var unit: String = ""
    var language: String = ""

    var itemList: MutableList<CityWeather> = mutableListOf()
    var adapter = WeatherAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myview = inflater.inflate(R.layout.fragment_search, container, false)
        searchButton = myview.findViewById<Button>(R.id.buttonSearch)
        searchCity = myview.findViewById<EditText>(R.id.et_search)
        searchButton.setOnClickListener() {
            search_button_fun(searchCity.text.toString())
        }

        progressBar = myview.findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.INVISIBLE

        recyclerView = myview.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(myview.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        return myview

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentSearch.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentSearch().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    // Verify Internet Connection
    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        } else {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }

        return result
    }

    private fun search_button_fun(city: String) {
        if (city == "") {
            Toast.makeText(myview.context, "Enter a City", Toast.LENGTH_SHORT).show()
        } else if (isInternetAvailable(myview.context) == false) {
            Toast.makeText(myview.context, "No Internet Connection", Toast.LENGTH_SHORT).show()
        } else {
            progressBar.visibility = View.VISIBLE
            getData(city)
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun getData(city: String) {
        itemList.clear()
        loadPreferences()
        val API_KEY = "7c8ff942cb22855b9299f55c068770dc"
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)



        api.getCityWeather(city, API_KEY, language, unit)
            .enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    val data = response.body()!!
                    itemList.addAll(data.list)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }


    // SharedPreferences
    private fun loadPreferences() {
        val sharedPreferences =
            myview.context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val celsiusTempSelected = sharedPreferences.getBoolean("CELSIUS SELECTED", true)
        val englishLanguageSelected = sharedPreferences.getBoolean("ENGLISH SELECTED", true)

        if (celsiusTempSelected == true) {
            unit = "metric"
        } else {
            unit = "imperial"
        }

        if (englishLanguageSelected == true) {
            language = "en"
        } else {
            language = "pt_br"
        }

    }

}