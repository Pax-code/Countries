package com.example.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countries.databinding.RecyclerRowBinding
import com.example.countries.models.Country
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import java.util.ArrayList

class CountryAdapter(private val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryHolder>() {

    class CountryHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerRowBinding.inflate(inflater, parent, false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val country = countryList[position]

        holder.binding.imageView.downloadFromUrl(country.countryImageUrl, placeHolderProgressBar(holder.binding.root.context))


        holder.binding.countryName.text = country.countryName
        holder.binding.region.text = country.countryRegion

        holder.binding.root.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(countryUUID = country.uuid)
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun updateCountryList(newCountryList: List<Country>){

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()

    }
}
