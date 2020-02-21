package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder

class ResponseAddPhoto(val persistedFaceId: String){
    companion object{
        fun fromJsonToModel(data: String) : ResponseAddPhoto{
            val gson = GsonBuilder().create()
            return gson.fromJson(data, ResponseAddPhoto::class.java)
        }
    }
}