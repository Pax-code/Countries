package com.example.countries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.models.Country
import com.example.countries.service.Database
import kotlinx.coroutines.launch
import java.util.UUID

class DetailsViewModel(application: Application):BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()


    fun getDataFromDetailsVM(uuid: Int){

        launch {

            val dao = Database(getApplication()).countryDao()
            val data = dao.getCountry(uuid)
            countryLiveData.value = data
        }
    }

}