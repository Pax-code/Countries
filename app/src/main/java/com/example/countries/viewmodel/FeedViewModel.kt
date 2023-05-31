package com.example.countries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.models.Country
import com.example.countries.service.APIService
import com.example.countries.service.Database
import com.example.countries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedViewModel(application: Application): BaseViewModel(application) {

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private var customPref = CustomSharedPreferences(getApplication())
    private val APIService = APIService()
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private val compositeDisposable = CompositeDisposable()

    fun refreshData(){

        val updateTime = customPref.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromApi()
        }
    }

    fun refreshFromApi(){
        getDataFromApi()
    }

    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = Database(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Got From SQLite Database", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getDataFromApi(){

        countryLoading.value = true

        compositeDisposable.add(
            APIService.getDataFromService()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>()
                {
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t)

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>){
        launch {
            val dao = Database(getApplication()).countryDao()
            dao.deleteAllDB()
            val listlong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while ( i < list.size){

                list[i].uuid = listlong[i].toInt()
                i += 1

            }

            showCountries(list)
        }
        customPref.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}