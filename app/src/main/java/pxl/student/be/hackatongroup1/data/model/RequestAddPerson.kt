package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder

class RequestAddPerson(val name: String) {
    fun fromModelToJson(): String{
        val gson = GsonBuilder().create()
        return gson.toJson(this, RequestAddPerson::class.java)
    }

    override fun toString(): String {
        return fromModelToJson()
    }
}