package pxl.student.be.hackatongroup1.data.model

import com.google.gson.GsonBuilder

class RequestDetect(val data: ByteArray) {
    fun fromModelToJson(): String{
        val gson = GsonBuilder().create()
        val model= gson.toJson(this, ResponseDetect::class.java)
        return model
    }

    override fun toString(): String {
        return fromModelToJson()
    }
}