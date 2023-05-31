package com.example.countries.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Country(

    @SerializedName("name")
    val countryName: String?,

    @SerializedName("region")
    val countryRegion: String?,

    @SerializedName("capital")
    val countryCapital: String?,

    @SerializedName("currency")
    val countryCurrency: String?,

    @SerializedName("language")
    val countryLanguage: String?,

    @SerializedName("flag")
    val countryImageUrl: String?

){
    @PrimaryKey(true)
    var uuid: Int = 0

}
