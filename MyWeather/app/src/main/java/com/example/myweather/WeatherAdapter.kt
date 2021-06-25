package com.example.myweather

import CityWeather
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class WeatherAdapter (private val list: MutableList<CityWeather>):

    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_item,
        parent, false)

        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = list[position]
        holder.name.text = currentItem.name
        holder.temp.text = currentItem.main.temp.toString()
    }

    override fun getItemCount() = list.size

    inner class WeatherViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.city_name)
        val temp : TextView = itemView.findViewById(R.id.city_temp)
        //val image : ImageView = itemView.findViewById(R.id.weather_img)
    }

}