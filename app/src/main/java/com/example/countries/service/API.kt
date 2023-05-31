package com.example.countries.service

import com.example.countries.models.Country
import io.reactivex.Single
import retrofit2.http.GET

interface API {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>

}