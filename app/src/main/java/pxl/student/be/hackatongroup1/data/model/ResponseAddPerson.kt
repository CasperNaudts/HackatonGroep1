package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class ResponseAddPerson (val personId: String){
    companion object{
        fun fromJsonToModel(data: String) : ResponseAddPerson{
            val gson = GsonBuilder().create()
            return gson.fromJson(data, ResponseAddPerson::class.java)
        }
    }
}