package com.example.myweather

import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class WeatherAdapter (private val list: ArrayList<WeatherItem>):
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_item,
        parent, false)

        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherItem = list[position]
        holder.name.text = weatherItem.name.toString()
        holder.temp.text = weatherItem.temp.toString()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class WeatherViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.city_name)
        val temp : TextView = itemView.findViewById(R.id.city_temp)
        val image : ImageView = itemView.findViewById(R.id.weather_img)
    }

}